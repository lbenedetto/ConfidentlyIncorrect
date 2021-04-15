package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import javax.persistence.*

@Entity
data class TblPlayer(
    @Id
    @GeneratedValue
    var id: Long?,

    var name: String,

    var score: Double,

    var isParticipating: Boolean,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "lobbyId"))
    var lobbyId: LobbyId,
)