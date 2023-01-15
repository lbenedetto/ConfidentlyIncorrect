package com.larsbenedetto.confidentlyincorrect.gateway.model

import org.hibernate.annotations.DynamicUpdate
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
@DynamicUpdate
data class TblEstimate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    var lobbyId: String,

    var playerId: Long,

    var questionId: Long,

    var lowerBound: Double,

    var upperBound: Double,

    var score: Double
)