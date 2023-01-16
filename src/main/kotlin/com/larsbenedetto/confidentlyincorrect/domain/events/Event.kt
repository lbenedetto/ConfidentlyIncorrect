package com.larsbenedetto.confidentlyincorrect.domain.events

interface Event {
    fun getEventType() : EventType
}


