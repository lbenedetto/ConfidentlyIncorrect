package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.usecase.service.NotificationController
import com.larsbenedetto.confidentlyincorrect.web.model.NextQuestionRequest
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NextQuestion(
    val lobbyGateway: LobbyGateway,
    val notificationController: NotificationController,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(lobbyId: LobbyId, request: NextQuestionRequest) {
        val lobby = lobbyGateway.getById(lobbyId)
        if (!accessTokenGateway.isValid(request.accessToken, lobby.ownerId)) {
            throw IllegalStateException("You do not have access to do this")
        }
        if (lobby.questionCount == lobby.questionLimit) {
            notificationController.notifyGameOver(lobbyId)
            return
        }
        val nextQuestionId = notificationController.notifyNextQuestion(lobby.id).nextQuestionId
        lobby.questionCount++
        lobby.questionId = nextQuestionId
        lobby.questionExpiresAt = LocalDateTime.now().plusMinutes(1);
        lobbyGateway.save(lobby)
    }
}