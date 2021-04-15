package com.larsbenedetto.confidentlyincorrect.domain.entity

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId

data class Lobby(
    var id: LobbyId,
    var ownerId: PlayerId,
    var questionId: QuestionId? = null,
    var capacity: Int?,
    var questionCount: Int = 0,
    var questionLimit: Int
)