package com.larsbenedetto.confidentlyincorrect.web.lobby.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId

data class JoinLobbyResponse(
    val playerId: PlayerId,
    val accessToken: AccessToken
)