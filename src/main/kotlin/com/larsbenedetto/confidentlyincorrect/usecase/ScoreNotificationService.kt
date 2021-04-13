package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.database.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.database.Player
import com.larsbenedetto.confidentlyincorrect.domain.database.Question
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.messages.NextQuestionNotification
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Service

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
        val nextQuestion = questionGateway.getRandomQuestion()

        val playerMap = players?.associateBy { player -> player.id }

        val scores = estimates?.map { estimate -> Score(
            score = estimate.score,
            playerName = playerMap!![estimate.playerId]!!.name,
            lowerBound = estimate.lowerBound,
            upperBound = estimate.upperBound
        ) }

        return NextQuestionNotification(
            nextQuestionId = nextQuestion.id,
            nextQuestionText = nextQuestion.text,
            lastQuestionAnswer = lastQuestion?.answer,
            lastQuestionScores = scores
        )
    }
}