package com.larsbenedetto.confidentlyincorrect.domain.database

import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId
import javax.persistence.*

@Entity
@Table(indexes = [
    Index(name = "idx_lobbyId_isParticipating", columnList = "lobbyId, isParticipating")
])
class Player(
    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: PlayerId,

    var name: String,

    var score: Double,

    var isParticipating: Boolean,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "lobbyId"))
    var lobbyId: LobbyId,
)