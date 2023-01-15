package com.larsbenedetto.confidentlyincorrect.domain

import com.larsbenedetto.confidentlyincorrect.util.RandomStringGenerator
import jakarta.persistence.Basic
import java.io.Serializable
import jakarta.persistence.Embeddable

interface Identity<T> : Serializable {
    val value: T
}

interface LongIdentity: Identity<Long> {}

interface StringIdentity: Identity<String> {}

@Embeddable
data class EstimateId(@field:Basic override val value: Long) : LongIdentity {
    constructor(value: String) : this(value.toLong())
}

@Embeddable
data class LobbyId(@field:Basic override val value: String) : StringIdentity {
    constructor() : this(RandomStringGenerator.generate(6, "ABCDEFGHIJKLMNOPQRSTUV"))
}

@Embeddable
data class PlayerId(@field:Basic override val value: Long) : LongIdentity {
    constructor(value: String) : this(value.toLong())
}

@Embeddable
data class QuestionId(override var value: Long): LongIdentity {
    constructor(value: String) : this(value.toLong())
}

@Embeddable
data class AccessToken(@field:Basic override val value: String) : StringIdentity