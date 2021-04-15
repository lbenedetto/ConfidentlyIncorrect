package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.usecase.service.ScoreNotificationService
import com.larsbenedetto.confidentlyincorrect.web.model.StartGameRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class StartGame(
    val lobbyGateway: LobbyGateway,
    val scoreNotificationService: ScoreNotificationService,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(lobbyId: LobbyId, request: StartGameRequest) {
        val lobby = lobbyGateway.getById(lobbyId)
        if (accessTokenGateway.isValid(request.accessToken, lobby.ownerId)) {
            throw IllegalStateException("You do not have access to do this")
        }
        val nextQuestionId = scoreNotificationService.notifyPlayers(lobby.id).nextQuestionId
        lobby.questionId = nextQuestionId
        lobby.questionExpiresAt = LocalDateTime.now().plusMinutes(1);
        lobbyGateway.save(lobby)
    }
}