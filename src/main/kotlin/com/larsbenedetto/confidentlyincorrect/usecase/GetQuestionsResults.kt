package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.gateway.EstimateGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import com.larsbenedetto.confidentlyincorrect.web.model.QuestionResults
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service

@Service
class GetQuestionsResults(
    val estimateGateway: EstimateGateway,
    val playerGateway: PlayerGateway,
    val questionGateway: QuestionGateway
) {
    fun execute(lobbyId: LobbyId, questionId: QuestionId): QuestionResults {
        val estimates = estimateGateway.findByLobbyAndQuestion(lobbyId, questionId)
        val players = playerGateway.findParticipatingByLobbyId(lobbyId)
        if (players.size != estimates.size) {
            throw ValidationException("Cannot view results before all players have answered")
        }
        val scores = Score.from(estimates, players)
        val question = questionGateway.getById(questionId)
        return QuestionResults(question, scores)
    }
}