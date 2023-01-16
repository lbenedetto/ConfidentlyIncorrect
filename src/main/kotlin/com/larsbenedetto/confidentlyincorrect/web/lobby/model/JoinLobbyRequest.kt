package com.larsbenedetto.confidentlyincorrect.web.lobby.model

data class JoinLobbyRequest(
    val playerName: String,
    val isParticipating: Boolean
)