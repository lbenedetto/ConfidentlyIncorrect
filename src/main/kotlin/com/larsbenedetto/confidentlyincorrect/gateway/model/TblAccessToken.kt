package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import javax.persistence.*

@Entity
data class TblAccessToken(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "playerId"))
    var playerId: PlayerId,

    var token: AccessToken
)