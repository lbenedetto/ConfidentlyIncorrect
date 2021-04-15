package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.entity.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.StringIdentity
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.web.model.CreateLobbyRequest
import com.larsbenedetto.confidentlyincorrect.web.model.CreateLobbyResponse
import com.larsbenedetto.confidentlyincorrect.web.model.LobbyDetails
import org.springframework.stereotype.Service

@Service
class CreateLobby(
    val lobbyGateway: LobbyGateway,
    val playerGateway: PlayerGateway,
    val accessTokenGateway: AccessTokenGateway
) {
    fun execute(request: CreateLobbyRequest): CreateLobbyResponse {
        val lobbyId = LobbyId(StringIdentity.generateIdValue(6))

        var player = Player(
            name = request.hostName,
            lobbyId = lobbyId,
            isParticipating = request.isParticipating
        )
        player = playerGateway.save(player)

        var lobby = Lobby(
            id = lobbyId,
            ownerId = player.id!!,
            capacity = request.capacity,
            questionLimit = request.questionLimit
        )
        lobby = lobbyGateway.save(lobby)

        val accessToken = accessTokenGateway.createForPlayerId(player.id!!)
        return CreateLobbyResponse(LobbyDetails(lobby, listOf(player)), accessToken)
    }
}