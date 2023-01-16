package com.larsbenedetto.confidentlyincorrect.domain.events

import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class NextQuestionEvent(
    val nextQuestionId: QuestionId?,
    val nextQuestionText: String?,
) : Event {
    override fun getEventType() = EventType.NEXT_QUESTION
}