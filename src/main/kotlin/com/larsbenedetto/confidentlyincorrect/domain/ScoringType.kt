package com.larsbenedetto.confidentlyincorrect.domain

import com.larsbenedetto.confidentlyincorrect.usecase.intervalExpansionFactor
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.log10

enum class ScoringType(
    val scale: Double,
    val adjustLowerBound: (Double) -> Double,
    val adjustUpperBound: (Double) -> Double,
    val calculateLowerBoundDistance: (Double, Double) -> Double,
    val calculateIntervalWidth: (Double, Double) -> Double,
    val calculateUpperBoundDistance: (Double, Double) -> Double,
) {
    Distance(100.0,
        { lowerBound -> lowerBound - intervalExpansionFactor },
        { upperBound -> upperBound + intervalExpansionFactor },
        { adjustedLowerBound, correctAnswer -> abs(adjustedLowerBound - correctAnswer) },
        { adjustedLowerBound, adjustedUpperBound -> abs(adjustedUpperBound - adjustedLowerBound) },
        { adjustedUpperBound, correctAnswer -> abs(correctAnswer - adjustedUpperBound) }
    ),
    OrdersOfMagnitude(
        ln(100.0),
        { lowerBound -> lowerBound * (1 - intervalExpansionFactor) },
        { upperBound -> upperBound * (1 + intervalExpansionFactor) },
        { adjustedLowerBound, correctAnswer -> abs(log10(adjustedLowerBound / correctAnswer)) },
        { adjustedLowerBound, adjustedUpperBound -> abs(log10(adjustedUpperBound / adjustedLowerBound)) },
        { adjustedUpperBound, correctAnswer -> abs(log10(correctAnswer / adjustedUpperBound)) }
    )
}