package com.larsbenedetto.confidentlyincorrect.usecase.service

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.entity.Question
import com.larsbenedetto.confidentlyincorrect.domain.messages.NextQuestionNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.PlayerAnsweredNotification
import com.larsbenedetto.confidentlyincorrect.domain.messages.PlayerJoinedNotification
import com.larsbenedetto.confidentlyincorrect.gateway.EstimateGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Service

@Service
class NotificationService(
    val questionGateway: QuestionGateway,
    val playerGateway: PlayerGateway,
    val estimateGateway: EstimateGateway,
) {
    @SendTo(NextQuestionNotification.TOPIC_NAME)
    fun notifyNextQuestion(
        @DestinationVariable lobbyId: LobbyId,
    ): NextQuestionNotification {
        val nextQuestion = questionGateway.getRandomQuestion(lobbyId)
        return NextQuestionNotification(
            nextQuestionId = nextQuestion.id,
            nextQuestionText = nextQuestion.text,
        )
    }

    @SendTo(PlayerAnsweredNotification.TOPIC_NAME)
    fun notifyPlayerAnswered(
        @DestinationVariable lobbyId: LobbyId,
        questionId: QuestionId,
    ): PlayerAnsweredNotification {
        val players = playerGateway.findParticipatingByLobbyId(lobbyId)
        val estimates = estimateGateway.findByLobbyAndQuestion(lobbyId, questionId)
        return PlayerAnsweredNotification(
            players.size,
            estimates.size,
        )
    }

    @SendTo(PlayerJoinedNotification.TOPIC_NAME)
    fun notifyPlayerJoined(
        @DestinationVariable lobbyId: LobbyId,
        player: Player,
        playerCount: Int,
        playerLimit: Int?
    ): PlayerJoinedNotification {
        return PlayerJoinedNotification(
            player,
            playerCount,
            playerLimit
        )
    }
}