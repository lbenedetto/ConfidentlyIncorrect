package com.larsbenedetto.confidentlyincorrect.web.lobby.model

import com.larsbenedetto.confidentlyincorrect.domain.entity.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class LobbyDetails(
    val lobby: Lobby,
    val players: List<Player>
)