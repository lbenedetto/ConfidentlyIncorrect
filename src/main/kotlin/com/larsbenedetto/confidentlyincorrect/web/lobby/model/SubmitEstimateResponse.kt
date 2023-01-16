package com.larsbenedetto.confidentlyincorrect.web.lobby.model

data class SubmitEstimateResponse(
    val score: Double,
    val otherAnswersCount: Int,
    val otherPlayersCount: Int,
)