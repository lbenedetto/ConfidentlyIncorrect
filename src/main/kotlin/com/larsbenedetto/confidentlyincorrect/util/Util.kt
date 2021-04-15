package com.larsbenedetto.confidentlyincorrect.util

fun <I, O> I?.mapNull(map: (I) -> O) : O? {
    return if(this != null) map(this) else null
}