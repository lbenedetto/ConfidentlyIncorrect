package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken

data class JoinLobbyResponse(
    val accessToken: AccessToken
)