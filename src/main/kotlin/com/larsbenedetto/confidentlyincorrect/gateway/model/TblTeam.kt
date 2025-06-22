package com.larsbenedetto.confidentlyincorrect.gateway.model

import org.hibernate.annotations.DynamicUpdate
import jakarta.persistence.*

@Entity
@DynamicUpdate
data class TblTeam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    var name: String,

    var score: Double,

    var lobbyId: String,
)
