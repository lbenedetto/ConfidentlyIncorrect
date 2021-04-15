package com.larsbenedetto.confidentlyincorrect.web.model

data class CreateLobbyRequest(
    val hostName: String,
    val isParticipating: Boolean,
    val capacity: Int?,
    val questionLimit: Int
)