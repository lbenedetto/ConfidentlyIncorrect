package com.larsbenedetto.confidentlyincorrect.domain.messages

data class PlayerAnsweredNotification(
    val playerCount: Int,
    val answerCount: Int
) {
    companion object {
        const val TOPIC_NAME = "/topics/{lobbyId}/playerAnswered"
    }
}
