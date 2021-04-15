package com.larsbenedetto.confidentlyincorrect.domain.entity

import com.larsbenedetto.confidentlyincorrect.domain.EstimateId
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class Estimate(
    var id: EstimateId?,
    var lobbyId: LobbyId,
    var playerId: PlayerId,
    var questionId: QuestionId,
    var lowerBound: Double,
    var upperBound: Double,
    var score: Double
)