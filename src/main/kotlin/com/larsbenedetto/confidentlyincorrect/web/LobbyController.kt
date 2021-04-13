package com.larsbenedetto.confidentlyincorrect.web

import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.database.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.database.Player
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.usecase.CreateLobby
import com.larsbenedetto.confidentlyincorrect.usecase.JoinLobby
import com.larsbenedetto.confidentlyincorrect.usecase.StartGame
import com.larsbenedetto.confidentlyincorrect.usecase.SubmitEstimate
import com.larsbenedetto.confidentlyincorrect.web.model.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("lobby/v1")
class LobbyController(
    val createLobby: CreateLobby,
    val joinLobby: JoinLobby,
    val startGame: StartGame,
    val submitEstimate: SubmitEstimate
) {
    @PostMapping("/")
    fun createLobby(
        @RequestBody request: CreateLobbyRequest
    ): ResponseEntity<ApiResponse<Lobby>> {
        val lobby = createLobby.execute(request)
        return ApiResponse.ok(lobby)
    }

    @PostMapping("/{lobbyId}/join")
    fun joinLobby(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @RequestBody request: JoinLobbyRequest
    ): ResponseEntity<ApiResponse<Player>> {
        val player = joinLobby.execute(lobbyId, request)
        return ApiResponse.ok(player)
    }

    @PostMapping("/{lobbyId}/start")
    fun startGame(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @RequestBody request: StartGameRequest
    ): ResponseEntity<ApiResponse<EmptyResponse>> {
        startGame.execute(lobbyId, request)
        return ApiResponse.ok()
    }

    @PostMapping("/{lobbyId}/submitEstimate")
    fun submitEstimate(
        @PathVariable("lobbyId") lobbyId: LobbyId,
        @RequestBody request: SubmitEstimateRequest
    ): ResponseEntity<ApiResponse<Score>> {
        val score = submitEstimate.execute(lobbyId, request)
        return ApiResponse.ok(score)
    }
}