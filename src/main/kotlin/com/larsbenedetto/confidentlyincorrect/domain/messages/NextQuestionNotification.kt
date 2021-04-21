package com.larsbenedetto.confidentlyincorrect.domain.messages

import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class NextQuestionNotification(
    val nextQuestionId: QuestionId,
    val nextQuestionText: String,
) {
    companion object {
        const val TOPIC_NAME = "/topic/lobby/{lobbyId}/nextQuestion"
    }
}