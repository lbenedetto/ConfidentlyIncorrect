package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken

data class SubmitEstimateRequest(
    val accessToken: AccessToken,
    val lowerBound: String,
    val upperBound: String
)