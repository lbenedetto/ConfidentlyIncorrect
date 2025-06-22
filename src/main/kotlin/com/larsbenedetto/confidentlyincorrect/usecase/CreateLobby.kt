package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.entity.Team
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.TeamGateway
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.CreateLobbyRequest
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.CreateLobbyResponse
import org.springframework.stereotype.Service

@Service
class CreateLobby(
    val lobbyGateway: LobbyGateway,
    val createPlayer: CreatePlayer,
    val teamGateway: TeamGateway
) {
    fun execute(request: CreateLobbyRequest): CreateLobbyResponse {
        val lobbyId = LobbyId()

        val team = teamGateway.save(Team(
            name = "Everyone",
            lobbyId = lobbyId
        ))

        val created = createPlayer.execute(lobbyId, team.id!!, request.hostName, request.isParticipating)

        val lobby = lobbyGateway.save(Lobby(
            id = lobbyId,
            ownerId = created.player.id!!,
            defaultTeamId = team.id,
            capacity = request.capacity,
            questionLimit = request.questionLimit
        ))

        return CreateLobbyResponse(created.player.id, lobby.id, created.accessToken)
    }
}
