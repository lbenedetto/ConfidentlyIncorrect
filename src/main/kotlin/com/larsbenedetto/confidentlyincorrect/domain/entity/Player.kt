package com.larsbenedetto.confidentlyincorrect.domain.entity

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId

data class Player(
    var id: PlayerId? = null,
    var name: String,
    var score: Double = 0.0,
    var isParticipating: Boolean = true,
    var lobbyId: LobbyId? = null,
)