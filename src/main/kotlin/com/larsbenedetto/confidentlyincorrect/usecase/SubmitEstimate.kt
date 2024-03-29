package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.events.PlayerAnsweredEvent
import com.larsbenedetto.confidentlyincorrect.gateway.*
import com.larsbenedetto.confidentlyincorrect.usecase.service.EventDispatcher
import com.larsbenedetto.confidentlyincorrect.usecase.service.ScoringService
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.SubmitEstimateRequest
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.SubmitEstimateResponse
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
    val eventDispatcher: EventDispatcher,
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

        val lowerBound = parseValue(request.lowerBound)
        val upperBound = parseValue(request.upperBound)

        val score = scoringService.calculateScore(
            scoringType = question.scoringType,
            correctAnswer = question.answer,
            lowerBound = lowerBound,
            upperBound = upperBound
        )
        player.score += score
        playerGateway.save(player)

        estimateGateway.submit(
            Estimate(
                id = null,
                lobbyId = lobby.id,
                playerId = player.id!!,
                questionId = question.id,
                lowerBound = lowerBound,
                upperBound = upperBound,
                score = score
            )
        )

        val players = playerGateway.findParticipatingByLobbyId(lobbyId)
        val estimates = estimateGateway.findByLobbyAndQuestion(lobbyId, question.id)
        val notification = eventDispatcher.notify(
            lobbyId = lobbyId,
            event = PlayerAnsweredEvent(
                players.size,
                estimates.size,
            )
        )
        return SubmitEstimateResponse(
            score = score,
            otherPlayersCount = notification.playerCount,
            otherAnswersCount = notification.answerCount
        )
    }

    private fun parseValue(value: String): Double {
        val trimmedValue = value.trim()

        if (trimmedValue.matches("\\d+k$".toRegex())) {
            return value.substring(0, value.length - 1).toInt() * 1_000.0
        }
        if (trimmedValue.matches("\\d+m$".toRegex())) {
            return value.substring(0, value.length - 1).toInt() * 1_000_000.0
        }
        if (trimmedValue.matches("\\d+b$".toRegex())) {
            return value.substring(0, value.length - 1).toInt() * 1_000_000_000.0
        }
        return value.toDouble()
    }
}