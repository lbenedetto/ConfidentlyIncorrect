package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class GetResultsRequest(
    val questionId: QuestionId
)