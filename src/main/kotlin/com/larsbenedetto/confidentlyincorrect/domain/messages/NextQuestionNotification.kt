package com.larsbenedetto.confidentlyincorrect.domain.messages

import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.database.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.identity.QuestionId

class NextQuestionNotification(
    val nextQuestionId: QuestionId,
    val nextQuestionText: String,
    val lastQuestionAnswer: Double?,
    val lastQuestionScores: List<Score>?
)