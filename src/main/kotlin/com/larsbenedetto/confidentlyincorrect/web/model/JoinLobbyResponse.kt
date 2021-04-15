package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class JoinLobbyResponse(
    val accessToken: AccessToken
)