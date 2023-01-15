package com.larsbenedetto.confidentlyincorrect.gateway.model

import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
@DynamicUpdate
data class TblLobby(

    @Id
    var id: String,

    var ownerId: Long,

    var questionId: Long?,

    var capacity: Int?,

    var questionCount: Int,

    var questionLimit: Int,

    var questionExpiresAt: LocalDateTime?
)