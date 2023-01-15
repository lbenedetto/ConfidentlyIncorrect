package com.larsbenedetto.confidentlyincorrect.gateway.model

import org.hibernate.annotations.DynamicUpdate
import jakarta.persistence.*

@Entity
@DynamicUpdate
data class TblAccessToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var playerId: Long,

    var token: String
)