package com.larsbenedetto.confidentlyincorrect.domain

import net.bytebuddy.utility.RandomString
import java.io.Serializable
import javax.persistence.Embeddable

interface Identity<T> : Serializable {
    val value: T
}

interface LongIdentity: Identity<Long> {}

interface StringIdentity: Identity<String> {
    companion object {
        fun generateIdValue(length: Int) = RandomString.make(length).toUpperCase()
    }
}

@Embeddable
data class EstimateId(override val value: Long) : LongIdentity {
    constructor(value: String) : this(value.toLong())
}

@Embeddable
data class LobbyId(override val value: String) : StringIdentity

@Embeddable
data class PlayerId(override val value: Long) : LongIdentity {
    constructor(value: String) : this(value.toLong())
}

@Embeddable
data class QuestionId(override var value: Long): LongIdentity {
    constructor(value: String) : this(value.toLong())
}

@Embeddable
data class AccessToken(override val value: String) : StringIdentity