package com.larsbenedetto.confidentlyincorrect.domain

import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player

data class Score(
    val score: Double,
    val playerName: String,
    val lowerBound: Double,
    val upperBound: Double
) {
    companion object {
        fun from(estimates: List<Estimate>, players: List<Player>): List<Score> {
            val playerMap = players.associateBy { player -> player.id }
            return estimates.map { estimate -> Score(
                score = estimate.score,
                playerName = playerMap[estimate.playerId]!!.name,
                lowerBound = estimate.lowerBound,
                upperBound = estimate.upperBound
            ) }
        }
    }

}