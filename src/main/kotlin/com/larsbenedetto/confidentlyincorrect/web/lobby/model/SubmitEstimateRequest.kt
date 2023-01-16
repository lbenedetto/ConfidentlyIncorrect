package com.larsbenedetto.confidentlyincorrect.web.lobby.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken

data class SubmitEstimateRequest(
    val accessToken: AccessToken,
    val lowerBound: String,
    val upperBound: String
)