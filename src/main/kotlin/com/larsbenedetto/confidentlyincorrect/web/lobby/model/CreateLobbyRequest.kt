package com.larsbenedetto.confidentlyincorrect.web.lobby.model

data class CreateLobbyRequest(
    val hostName: String,
    val isParticipating: Boolean,
    val capacity: Int?,
    val questionLimit: Int
)