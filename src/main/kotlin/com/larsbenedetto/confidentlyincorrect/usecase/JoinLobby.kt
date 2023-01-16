package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.events.PlayerJoinedEvent
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.usecase.service.EventDispatcher
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.JoinLobbyRequest
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.JoinLobbyResponse
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service

@Service
class JoinLobby(
    val createPlayer: CreatePlayer,
    val playerGateway: PlayerGateway,
    val lobbyGateway: LobbyGateway,
    val eventDispatcher: EventDispatcher,
) {
    fun execute(lobbyId: LobbyId, request: JoinLobbyRequest): JoinLobbyResponse {
        val lobby = lobbyGateway.getById(lobbyId)
        val players = playerGateway.findByLobbyId(lobbyId)
        if (lobby.capacity == players.size) {
            throw ValidationException("Cannot join a full lobby")
        }

        val created = createPlayer.execute(request.playerName, request.isParticipating)
        created.player.lobbyId = lobbyId
        playerGateway.save(created.player)

        eventDispatcher.notify(
            lobbyId = lobbyId,
            event = PlayerJoinedEvent(
                player = created.player,
                playerCount = players.size + 1,
                playerLimit = lobby.capacity
            )
        )

        return JoinLobbyResponse(
            created.player.id!!,
            created.accessToken,
        )
    }
}