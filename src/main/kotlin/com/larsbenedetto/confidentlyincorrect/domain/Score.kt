package com.larsbenedetto.confidentlyincorrect.domain

data class Score(
    val value: Double,
    val cumulativeScore: Double,
    val playerName: String,
    val lowerBound: Double,
    val upperBound: Double
)