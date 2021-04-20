package com.larsbenedetto.confidentlyincorrect.web.model

data class JoinLobbyRequest(
    val playerName: String,
    val isParticipating: Boolean
)