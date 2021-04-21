package com.larsbenedetto.confidentlyincorrect.usecase.service

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.messages.NextQuestionNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.PlayerAnsweredNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.PlayerJoinedNotification
import com.larsbenedetto.confidentlyincorrect.gateway.EstimateGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.SendTo
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
        val notification = NextQuestionNotification(
            nextQuestionId = nextQuestion.id,
            nextQuestionText = nextQuestion.text,
        )
        simpMessagingTemplate.convertAndSend(getDestination(NextQuestionNotification.TOPIC_NAME, lobbyId), notification)
        return notification
    }

    fun notifyPlayerAnswered(
        lobbyId: LobbyId,
        questionId: QuestionId,
    ): PlayerAnsweredNotification {
        val players = playerGateway.findParticipatingByLobbyId(lobbyId)
        val estimates = estimateGateway.findByLobbyAndQuestion(lobbyId, questionId)
        val notification = PlayerAnsweredNotification(
            players.size,
            estimates.size,
        )
        simpMessagingTemplate.convertAndSend(getDestination(PlayerAnsweredNotification.TOPIC_NAME, lobbyId), notification)
        return notification
    }

    fun notifyPlayerJoined(
        lobbyId: LobbyId,
        player: Player,
        playerCount: Int,
        playerLimit: Int?
    ): PlayerJoinedNotification {
        val notification = PlayerJoinedNotification(
            player,
            playerCount,
            playerLimit
        )
        simpMessagingTemplate.convertAndSend(getDestination(PlayerJoinedNotification.TOPIC_NAME, lobbyId), notification)
        return notification
    }

    fun getDestination(topicName: String, lobbyId: LobbyId) : String {
        return topicName.replace("{lobbyId}", lobbyId.value)
    }
}