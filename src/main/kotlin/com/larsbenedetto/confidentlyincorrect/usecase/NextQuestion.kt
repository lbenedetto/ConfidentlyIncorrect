package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.events.GameOverEvent
import com.larsbenedetto.confidentlyincorrect.domain.events.NextQuestionEvent
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import com.larsbenedetto.confidentlyincorrect.usecase.service.EventDispatcher
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.NextQuestionRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NextQuestion(
    val lobbyGateway: LobbyGateway,
    val questionGateway: QuestionGateway,
    val eventDispatcher: EventDispatcher,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(lobbyId: LobbyId, request: NextQuestionRequest) {
        val lobby = lobbyGateway.getById(lobbyId)
        if (!accessTokenGateway.isValid(request.accessToken, lobby.ownerId)) {
            throw IllegalStateException("You do not have access to do this")
        }
        if (lobby.questionCount == lobby.questionLimit) {
            eventDispatcher.notify(lobbyId, GameOverEvent())
            return
        }
        val nextQuestion = questionGateway.getRandomQuestion(lobbyId)
        val nextQuestionEvent = eventDispatcher.notify(
            lobbyId = lobbyId,
            event = NextQuestionEvent(
                nextQuestionId = nextQuestion.id,
                nextQuestionText = nextQuestion.text,
            )
        )
        lobby.questionCount++
        lobby.questionId = nextQuestionEvent.nextQuestionId
        lobby.questionExpiresAt = LocalDateTime.now().plusMinutes(1);
        lobbyGateway.save(lobby)
    }
}