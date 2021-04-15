package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken

data class StartGameRequest(
    var accessToken: AccessToken
)