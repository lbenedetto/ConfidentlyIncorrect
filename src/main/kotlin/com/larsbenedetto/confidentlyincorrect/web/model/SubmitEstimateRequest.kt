package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId

class SubmitEstimateRequest(
    val playerId: PlayerId,
    val lowerBound: Double,
    val upperBound: Double
)