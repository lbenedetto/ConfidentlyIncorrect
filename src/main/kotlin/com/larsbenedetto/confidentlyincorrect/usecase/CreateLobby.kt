package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.StringIdentity
import com.larsbenedetto.confidentlyincorrect.domain.entity.Lobby
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.web.model.CreateLobbyRequest
import com.larsbenedetto.confidentlyincorrect.web.model.CreateLobbyResponse
import org.springframework.stereotype.Service

@Service
class CreateLobby(
    val lobbyGateway: LobbyGateway,
    val createPlayer: CreatePlayer,
    val playerGateway: PlayerGateway
) {
    fun execute(request: CreateLobbyRequest): CreateLobbyResponse {
        val created = createPlayer.execute(request.hostName, request.isParticipating)

        val lobbyId = LobbyId()
        var lobby = Lobby(
            id = lobbyId,
            ownerId = created.player.id!!,
            capacity = request.capacity,
            questionLimit = request.questionLimit
        )
        lobby = lobbyGateway.save(lobby)

        created.player.lobbyId = lobbyId
        playerGateway.save(created.player)

        return CreateLobbyResponse(lobby.id, created.accessToken)
    }
}