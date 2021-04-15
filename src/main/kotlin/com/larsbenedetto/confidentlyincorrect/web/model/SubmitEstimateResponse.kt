package com.larsbenedetto.confidentlyincorrect.web.model

data class SubmitEstimateResponse(
    val score: Double,
    val otherAnswersCount: Int,
    val otherPlayersCount: Int,
)