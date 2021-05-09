package com.larsbenedetto.confidentlyincorrect.usecase.service

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.messages.*
import com.larsbenedetto.confidentlyincorrect.gateway.EstimateGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class NotificationController(
    val questionGateway: QuestionGateway,
    val playerGateway: PlayerGateway,
    val estimateGateway: EstimateGateway,
    val simpMessagingTemplate: SimpMessagingTemplate
) {
    fun notifyNextQuestion(
        lobbyId: LobbyId,
    ): NextQuestionNotification {
        val nextQuestion = questionGateway.getRandomQuestion(lobbyId)
        val notification =
            NextQuestionNotification(
                nextQuestionId = nextQuestion.id,
                nextQuestionText = nextQuestion.text,
            )
        lobbyId.sendNotification(notification)
        return notification
    }

    fun notifyGameOver(lobbyId: LobbyId, ) {
        lobbyId.sendNotification(GameOverNotification())
    }

    fun notifyPlayerAnswered(
        lobbyId: LobbyId,
        questionId: QuestionId,
    ): PlayerAnsweredNotification {
        val players = playerGateway.findParticipatingByLobbyId(lobbyId)
        val estimates = estimateGateway.findByLobbyAndQuestion(lobbyId, questionId)
        val notification =
            PlayerAnsweredNotification(
                players.size,
                estimates.size,
            )
        lobbyId.sendNotification(notification)
        return notification
    }

    fun notifyPlayerJoined(
        lobbyId: LobbyId,
        player: Player,
        playerCount: Int,
        playerLimit: Int?
    ): PlayerJoinedNotification {
        val notification =
            PlayerJoinedNotification(
                player,
                playerCount,
                playerLimit
            )
        lobbyId.sendNotification(notification)
        return notification
    }

    private fun LobbyId.sendNotification(notification: Notification) {
        val destination = notification.getTopicName().replace("{lobbyId}", this.value)
        simpMessagingTemplate.convertAndSend(destination, notification)
    }
}