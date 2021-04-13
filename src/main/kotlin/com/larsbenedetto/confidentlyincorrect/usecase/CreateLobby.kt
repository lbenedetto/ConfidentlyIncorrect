package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.database.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.StringIdentity
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.web.model.CreateLobbyRequest
import org.springframework.stereotype.Service

@Service
class CreateLobby(
    val lobbyGateway: LobbyGateway,
    val playerGateway: PlayerGateway
) {
    fun execute(request: CreateLobbyRequest) : Lobby {
        val lobbyId = LobbyId(StringIdentity.generateIdValue(6))
        val player = playerGateway.createPlayer(request.hostName, lobbyId, request.isParticipating)
        return lobbyGateway.createLobby(lobbyId, player.id)
    }
}