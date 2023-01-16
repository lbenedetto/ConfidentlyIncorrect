package com.larsbenedetto.confidentlyincorrect.web.lobby.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId

data class CreateLobbyResponse(
    val playerId: PlayerId,
    val lobbyId: LobbyId,
    val accessToken: AccessToken
)