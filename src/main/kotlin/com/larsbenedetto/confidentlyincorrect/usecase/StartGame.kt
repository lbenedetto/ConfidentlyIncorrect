package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.web.model.StartGameRequest
import org.springframework.stereotype.Service

@Service
class StartGame(
    val lobbyGateway: LobbyGateway,
    val scoreNotificationService: ScoreNotificationService
) {
    fun execute(lobbyId: LobbyId, request: StartGameRequest) {
        val lobby = lobbyGateway.getById(lobbyId)
        if (lobby.ownerId != request.playerId) {
            throw IllegalStateException("You do not have access to do this")
        }
        val nextQuestionId = scoreNotificationService.notifyPlayers(lobby.id).nextQuestionId
        lobby.questionId = nextQuestionId
        lobbyGateway.save(lobby)

    }
}