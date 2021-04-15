package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken

data class CreateLobbyResponse(
    val lobby: LobbyDetails,
    val accessToken: AccessToken
)