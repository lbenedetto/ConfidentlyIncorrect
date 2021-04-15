package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.gateway.AccessTokenGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.usecase.service.NotificationService
import com.larsbenedetto.confidentlyincorrect.web.model.JoinLobbyRequest
import com.larsbenedetto.confidentlyincorrect.web.model.JoinLobbyResponse
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service

@Service
class JoinLobby(
    val createPlayer: CreatePlayer,
    val playerGateway: PlayerGateway,
    val lobbyGateway: LobbyGateway,
    val notificationService: NotificationService,
) {
    fun execute(lobbyId: LobbyId, request: JoinLobbyRequest): JoinLobbyResponse {
        val lobby = lobbyGateway.getById(lobbyId)
        val players = playerGateway.findByLobbyId(lobbyId)
        if (lobby.capacity == players.size) {
            throw ValidationException("Cannot join a full lobby")
        }

        val created = createPlayer.execute(request.playerName)
        created.player.lobbyId = lobbyId
        playerGateway.save(created.player)

        notificationService.notifyPlayerJoined(
            lobbyId = lobbyId,
            player = created.player,
            playerCount = players.size + 1,
            playerLimit = lobby.capacity
        )

        return JoinLobbyResponse(created.accessToken)
    }
}