package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import javax.persistence.*

@Entity
data class TblEstimate(
    @Id
    @GeneratedValue
    var id: Long?,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "lobbyId"))
    var lobbyId: LobbyId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "playerId"))
    var playerId: PlayerId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "questionId"))
    var questionId: QuestionId,

    var lowerBound: Double,

    var upperBound: Double,

    var score: Double
)