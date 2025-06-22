package com.larsbenedetto.confidentlyincorrect.domain.entity

import com.larsbenedetto.confidentlyincorrect.domain.EstimateId
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.TeamId

data class Estimate(
    var id: EstimateId?,
    var lobbyId: LobbyId,
    var playerId: PlayerId?,
    var teamId: TeamId,
    var questionId: QuestionId,
    var lowerBound: Double,
    var upperBound: Double,
    var score: Double
)
