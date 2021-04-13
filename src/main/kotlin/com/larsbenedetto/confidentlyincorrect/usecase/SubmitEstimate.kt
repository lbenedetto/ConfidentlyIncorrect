package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.database.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.EstimateGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import com.larsbenedetto.confidentlyincorrect.web.model.SubmitEstimateRequest
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service

@Service
class SubmitEstimate(
    val playerGateway: PlayerGateway,
    val questionGateway: QuestionGateway,
    val estimateGateway: EstimateGateway,
    val lobbyGateway: LobbyGateway,
    val scoringService: ScoringService,
    val scoreNotificationService: ScoreNotificationService
) {
    fun execute(lobbyId: LobbyId, request: SubmitEstimateRequest): Score {
        val player = playerGateway.getById(request.playerId)
        if (!player.isParticipating) {
            throw ValidationException("Inactive player cannot answer questions")
        }
        if (player.lobbyId != lobbyId) {
            throw ValidationException("Player is not a member of this lobby")
        }

        val lobby = lobbyGateway.getById(lobbyId)
        val question = questionGateway.getById(lobby.questionId!!)

        estimateGateway.findByLobbyAndQuestionAndPlayer(lobby.id, question.id, player.id)
            .ifPresent { throw ValidationException("Cannot submit multiple estimates") }

        val score = scoringService.calculateScore(
            scoringType = question.scoringType,
            correctAnswer = question.answer,
            lowerBound = request.lowerBound,
            upperBound = request.upperBound
        )
        player.score += score
        playerGateway.save(player)

        estimateGateway.submit(
            Estimate(
                id = null,
                lobbyId = lobby.id,
                playerId = player.id,
                questionId = question.id,
                lowerBound = request.lowerBound,
                upperBound = request.upperBound,
                score = score
            )
        )

        val players = playerGateway.findParticipatingByLobbyId(lobby.id)
        val estimates = estimateGateway.findByLobbyAndQuestion(lobby.id, question.id)
        if (estimates.size == players.size) {
            scoreNotificationService.notifyPlayers(lobby.id, question, estimates, players)
        }

        return Score(
            score = score,
            playerName = player.name,
            lowerBound = request.lowerBound,
            upperBound = request.upperBound
        )
    }
}