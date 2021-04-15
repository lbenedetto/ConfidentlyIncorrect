package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class CreatePlayerResponse (
    val player: Player,
    val accessToken: AccessToken
)