package com.larsbenedetto.confidentlyincorrect.domain.database

import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.identity.QuestionId
import javax.persistence.*

@Entity
@Table(indexes = [
    Index(name = "idx_ownerId", columnList = "ownerId"),
    Index(name = "idx_questionId", columnList = "questionId")
])
class Lobby(

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: LobbyId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "ownerId"))
    var ownerId: PlayerId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "questionId"))
    var questionId: QuestionId?
)