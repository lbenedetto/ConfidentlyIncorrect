package com.larsbenedetto.confidentlyincorrect.domain.events

class GameOverEvent : Event {
    override fun getEventType() = EventType.GAME_OVER

}
