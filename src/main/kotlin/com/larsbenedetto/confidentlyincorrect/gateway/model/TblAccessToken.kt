package com.larsbenedetto.confidentlyincorrect.gateway.model

import com.larsbenedetto.confidentlyincorrect.domain.AccessToken
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
data class TblAccessToken(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id: Long? = null,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "playerId"))
    var playerId: PlayerId,

    var token: AccessToken
)