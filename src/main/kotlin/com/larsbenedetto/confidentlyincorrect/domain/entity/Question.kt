package com.larsbenedetto.confidentlyincorrect.domain.entity

import com.larsbenedetto.confidentlyincorrect.domain.QuestionCategory
import com.larsbenedetto.confidentlyincorrect.domain.ScoringType
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class Question(
    var id: QuestionId,
    var scoringType: ScoringType,
    var text: String,
    var answer: Double,
    var explanation: String,
    var category: QuestionCategory,
)