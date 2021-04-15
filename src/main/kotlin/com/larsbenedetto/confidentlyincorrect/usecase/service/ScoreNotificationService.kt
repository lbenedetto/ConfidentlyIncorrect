package com.larsbenedetto.confidentlyincorrect.usecase.service

import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Question
import com.larsbenedetto.confidentlyincorrect.domain.messages.NextQuestionNotification
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class ScoreNotificationService(
    val questionGateway: QuestionGateway,
) {
    @SendTo("/topic/estimates/{lobbyId}")
    fun notifyPlayers(
        @DestinationVariable lobbyId: LobbyId,
        lastQuestion: Question? = null,
        estimates: List<Estimate>? = null,
        players: List<Player>? = null,
    ): NextQuestionNotification {
        val nextQuestion = questionGateway.getRandomQuestion(lobbyId)
        return NextQuestionNotification(
            nextQuestionId = nextQuestion.id,
            nextQuestionText = nextQuestion.text,

        )
    }
}