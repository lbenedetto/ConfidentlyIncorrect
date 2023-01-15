package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.QuestionCategory
import com.larsbenedetto.confidentlyincorrect.domain.ScoringType
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import jakarta.persistence.*

@Entity
data class TblQuestion(
    @Id
    var id: Long,

    @Enumerated
    var scoringType: ScoringType,

    var text: String,

    var answer: Double,

    var explanation: String,

    @Enumerated(EnumType.STRING)
    var category: QuestionCategory,

)