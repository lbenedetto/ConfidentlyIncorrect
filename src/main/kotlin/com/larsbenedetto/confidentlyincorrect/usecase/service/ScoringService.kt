package com.larsbenedetto.confidentlyincorrect.usecase.service
import com.larsbenedetto.confidentlyincorrect.domain.ScoringType
import org.springframework.stereotype.Service
import kotlin.math.*

const val maxScore = 10.0 // smax
const val minScore = -57.26893683880667 // smin
const val maxConfidence = 0.99 // pmax
const val confidence = .90 // β
const val intervalExpansionFactor = 0.4 // δ

@Service
/**
 * https://arxiv.org/pdf/1808.07501.pdf
 */
class ScoringService {
    fun calculateScore(
        scoringType: ScoringType,
        correctAnswer: Double,
        lowerBound: Double,
        upperBound: Double
    ): Double {
        val adjustedLowerBound = scoringType.adjustLowerBound(lowerBound)
        val adjustedUpperBound = scoringType.adjustUpperBound(upperBound)
        val adjustedScore =calculateScoreWithAdjustedBounds(scoringType, correctAnswer, adjustedLowerBound, adjustedUpperBound);
        val score = calculateScoreWithAdjustedBounds(scoringType, correctAnswer, lowerBound, upperBound)
        return max(score, adjustedScore)
    }

    private fun calculateScoreWithAdjustedBounds(
        scoringType: ScoringType,
        correctAnswer: Double,
        lowerBound: Double,
        upperBound: Double
    ): Double {
        if(lowerBound == upperBound && lowerBound == correctAnswer) {
            return maxScore
        }

        // How far between the lower bound and the correct answer
        val lowerBoundDistance = scoringType.calculateLowerBoundDistance(lowerBound, correctAnswer) / scoringType.scale
        // How far between the upper bound and the correct answer
        val upperBoundDistance = scoringType.calculateUpperBoundDistance(correctAnswer, upperBound) / scoringType.scale
        // How far between the lower and upper bound
        val intervalWidth = scoringType.calculateIntervalWidth(lowerBound, upperBound) / scoringType.scale

        val score = when {
            correctAnswer < lowerBound -> {
                ((-2 / (1 - confidence)) * lowerBoundDistance) - ((lowerBoundDistance / (1 + lowerBoundDistance)) * intervalWidth)
            }
            correctAnswer > upperBound -> {
                ((-2 / (1 - confidence)) * upperBoundDistance) - ((upperBoundDistance / (1 + upperBoundDistance)) * intervalWidth)
            }
            else -> {
                4 * maxScore * ((lowerBoundDistance * upperBoundDistance) / intervalWidth.pow(2)) * (1 - (intervalWidth / (1 + intervalWidth)))
            }
        }
        return max(minScore, min(maxScore, score))
    }
}


