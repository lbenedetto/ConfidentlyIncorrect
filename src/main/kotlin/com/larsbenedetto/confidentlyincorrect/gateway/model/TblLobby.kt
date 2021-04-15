package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class TblLobby(

    @Id
    var id: String,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "ownerId"))
    var ownerId: PlayerId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "questionId"))
    var questionId: QuestionId?,

    var capacity: Int?,

    var questionCount: Int,

    var questionLimit: Int,

    var questionExpirationDate: LocalDateTime
)