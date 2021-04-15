package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.web.model.JoinLobbyRequest
import com.larsbenedetto.confidentlyincorrect.web.model.JoinLobbyResponse
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service

@Service
class JoinLobby(
    val playerGateway: PlayerGateway,
    val lobbyGateway: LobbyGateway,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(lobbyId: LobbyId, request: JoinLobbyRequest): JoinLobbyResponse {
        val lobby = lobbyGateway.getById(lobbyId)
        val players = playerGateway.findByLobbyId(lobbyId)
        if (lobby.capacity == players.size) {
            throw ValidationException("Cannot join a full lobby")
        }

        var player = Player(
            name = request.playerName,
            lobbyId = lobbyId,
        )
        player = playerGateway.save(player)

        val accessToken = accessTokenGateway.createForPlayerId(player.id!!)
        return JoinLobbyResponse(player, accessToken)
    }
}