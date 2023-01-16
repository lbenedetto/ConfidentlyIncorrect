package com.larsbenedetto.confidentlyincorrect.web.lobby.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken

data class NextQuestionRequest(
    var accessToken: AccessToken
)