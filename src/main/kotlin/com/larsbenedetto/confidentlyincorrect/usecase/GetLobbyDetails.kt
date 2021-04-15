package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.web.model.LobbyDetails
import org.springframework.stereotype.Service

@Service
class GetLobbyDetails(
    val lobbyGateway: LobbyGateway,
    val playerGateway: PlayerGateway
) {
    fun execute(lobbyId: LobbyId): LobbyDetails {
        val lobby = lobbyGateway.getById(lobbyId)
        val players = playerGateway.findByLobbyId(lobbyId)
        return LobbyDetails(lobby, players)
    }
}