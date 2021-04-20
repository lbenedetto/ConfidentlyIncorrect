package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.usecase.service.NotificationService
import com.larsbenedetto.confidentlyincorrect.web.model.NextQuestionRequest
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NextQuestion(
    val lobbyGateway: LobbyGateway,
    val scoreNotificationService: NotificationService,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(lobbyId: LobbyId, request: NextQuestionRequest) {
        val lobby = lobbyGateway.getById(lobbyId)
        if (!accessTokenGateway.isValid(request.accessToken, lobby.ownerId)) {
            throw IllegalStateException("You do not have access to do this")
        }
        if (lobby.questionExpiresAt?.isBefore(LocalDateTime.now()) == true) {
            throw ValidationException("Cannot skip question")
        }
        val nextQuestionId = scoreNotificationService.notifyNextQuestion(lobby.id).nextQuestionId
        lobby.questionId = nextQuestionId
        lobby.questionExpiresAt = LocalDateTime.now().plusMinutes(1);
        lobbyGateway.save(lobby)
    }
}