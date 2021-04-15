package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.QuestionCategory
import com.larsbenedetto.confidentlyincorrect.domain.ScoringType
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import javax.persistence.*

@Entity
data class TblQuestion(
    @Id
    var id: QuestionId,

    @Enumerated
    var scoringType: ScoringType,

    var text: String,

    var answer: Double,

    @Enumerated
    var category: QuestionCategory,

    var expirationDate: Long
)