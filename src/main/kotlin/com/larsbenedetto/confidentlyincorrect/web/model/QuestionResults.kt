package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.entity.Question

data class QuestionResults(
    val question: Question,
    val gameOver: Boolean,
    val scores: List<Score>
)