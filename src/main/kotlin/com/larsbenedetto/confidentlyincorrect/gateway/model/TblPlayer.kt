package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
data class TblPlayer(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id: Long?,

    var name: String,

    var score: Double,

    var isParticipating: Boolean,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "lobbyId"))
    var lobbyId: LobbyId,
)