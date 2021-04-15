package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId

data class CreateLobbyResponse(
    val lobbyId: LobbyId,
    val accessToken: AccessToken
)