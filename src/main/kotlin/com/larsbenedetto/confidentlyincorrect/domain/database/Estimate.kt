package com.larsbenedetto.confidentlyincorrect.domain.database

import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.identity.QuestionId
import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(name = "idx_lobbyId_questionId", columnList = "lobbyId, questionId"),
        Index(name = "idx_questionId", columnList = "questionId"),
        Index(name = "idx_playerId", columnList = "playerId")
    ]
)
class Estimate(
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