package com.larsbenedetto.confidentlyincorrect.domain

import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class Score(
    val value: Double,
    val cumulativeScore: Double,
    val playerName: String,
    val lowerBound: Double,
    val upperBound: Double
) {
    companion object {
        fun from(estimates: List<Estimate>, players: List<Player>): List<Score> {
            val playerMap = players.associateBy { player -> player.id }
            return estimates.map { estimate ->
                val player = playerMap[estimate.playerId]!!
                Score(
                    value = estimate.score,
                    cumulativeScore = player.score,
                    playerName = player.name,
                    lowerBound = estimate.lowerBound,
                    upperBound = estimate.upperBound
                )
            }
        }
    }
}