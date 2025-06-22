package com.larsbenedetto.confidentlyincorrect.domain.entity

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.TeamId

data class Team(
    val id: TeamId? = null,
    val name: String,
    var score: Double = 0.0,
    val lobbyId: LobbyId,
)
