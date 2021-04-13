package com.larsbenedetto.confidentlyincorrect.domain.database

import com.larsbenedetto.confidentlyincorrect.domain.QuestionCategory
import com.larsbenedetto.confidentlyincorrect.domain.ScoringType
import com.larsbenedetto.confidentlyincorrect.domain.identity.QuestionId
import javax.persistence.*

@Entity
class Question(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: QuestionId,

    @Enumerated
    @AttributeOverride(name = "value", column = Column(name = "scoringType"))
    var scoringType: ScoringType,

    var text: String,

    var answer: Double,

    @Enumerated
    @AttributeOverride(name = "value", column = Column(name = "category"))
    var category: QuestionCategory,
)