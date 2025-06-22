package com.larsbenedetto.confidentlyincorrect.domain

data class Score(
    val value: Double,
    val cumulativeScore: Double,
    val scorerName: String,
    val teamId: TeamId,
    val isPlayer: Boolean,
    val lowerBound: Double,
    val upperBound: Double
)
