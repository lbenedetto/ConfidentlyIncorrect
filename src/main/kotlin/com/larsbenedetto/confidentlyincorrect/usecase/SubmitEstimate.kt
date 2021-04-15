package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.gateway.*
import com.larsbenedetto.confidentlyincorrect.usecase.service.NotificationService
import com.larsbenedetto.confidentlyincorrect.usecase.service.ScoringService
import com.larsbenedetto.confidentlyincorrect.web.model.SubmitEstimateRequest
import com.larsbenedetto.confidentlyincorrect.web.model.SubmitEstimateResponse
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SubmitEstimate(
    val playerGateway: PlayerGateway,
    val questionGateway: QuestionGateway,
    val estimateGateway: EstimateGateway,
    val lobbyGateway: LobbyGateway,
    val scoringService: ScoringService,
    val notificationService: NotificationService,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(lobbyId: LobbyId, request: SubmitEstimateRequest): SubmitEstimateResponse {
        val playerId = accessTokenGateway.getPlayerIdByAccessToken(request.accessToken)
        val player = playerGateway.getById(playerId)
        if (player.lobbyId != lobbyId) {
            throw ValidationException("Player is not a member of this lobby")
        }
        if (!player.isParticipating) {
            throw ValidationException("Inactive player cannot answer questions")
        }

        val lobby = lobbyGateway.getById(lobbyId)
        if (lobby.questionExpiresAt!!.isBefore(LocalDateTime.now())) {
            throw ValidationException("Question cannot be answered after its lifetime")
        }

        val question = questionGateway.getById(lobby.questionId!!)
        estimateGateway.findPlayersEstimateForQuestionInLobby(lobby.id, question.id, player.id!!)
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
                playerId = player.id!!,
                questionId = question.id,
                lowerBound = request.lowerBound,
                upperBound = request.upperBound,
                score = score
            )
        )

        val notification = notificationService.notifyPlayerAnswered(lobbyId, question.id)
        return SubmitEstimateResponse(
            score = score,
            otherPlayersCount = notification.playerCount,
            otherAnswersCount = notification.answerCount
        )
    }
}