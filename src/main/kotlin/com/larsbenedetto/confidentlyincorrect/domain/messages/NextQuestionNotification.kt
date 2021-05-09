package com.larsbenedetto.confidentlyincorrect.domain.messages

import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class NextQuestionNotification(
    val nextQuestionId: QuestionId?,
    val nextQuestionText: String?,
) : Notification {
    override fun getTopicName() = TOPIC_NAME
    companion object {
        const val TOPIC_NAME = "/topic/lobby/{lobbyId}/nextQuestion"
    }
}