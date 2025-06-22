package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.TeamId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import org.springframework.stereotype.Service

@Service
class CreatePlayer(
    val playerGateway: PlayerGateway,
    val accessTokenGateway: AccessTokenGateway,
) {
    fun execute(
        lobbyId: LobbyId,
        teamId: TeamId,
        name: String,
        isParticipating: Boolean = true
    ): CreatePlayerResponse {
        var player = Player(
            lobbyId = lobbyId,
            teamId = teamId,
            name = name,
            isParticipating = isParticipating
        )
        player = playerGateway.save(player)

        val accessToken = accessTokenGateway.createForPlayerId(player.id!!)
        return CreatePlayerResponse(player, accessToken)
    }
}
