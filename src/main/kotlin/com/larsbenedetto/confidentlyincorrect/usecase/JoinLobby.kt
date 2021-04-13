package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.database.Player
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.web.model.JoinLobbyRequest
import org.springframework.stereotype.Service

@Service
class JoinLobby(
    val playerGateway: PlayerGateway
) {
    fun execute(lobbyId: LobbyId, request: JoinLobbyRequest): Player {
        return playerGateway.createPlayer(request.playerName, lobbyId)
    }
}