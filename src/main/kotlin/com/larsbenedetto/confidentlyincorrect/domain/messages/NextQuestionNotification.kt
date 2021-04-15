package com.larsbenedetto.confidentlyincorrect.domain.messages

import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class NextQuestionNotification(
    val nextQuestionId: QuestionId,
    val nextQuestionText: String,
)