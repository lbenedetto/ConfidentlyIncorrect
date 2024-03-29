package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.gateway.EstimateGateway
import com.larsbenedetto.confidentlyincorrect.gateway.LobbyGateway
import com.larsbenedetto.confidentlyincorrect.gateway.PlayerGateway
import com.larsbenedetto.confidentlyincorrect.gateway.QuestionGateway
import com.larsbenedetto.confidentlyincorrect.web.lobby.model.QuestionResults
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GetQuestionsResults(
    val lobbyGateway: LobbyGateway,
    val estimateGateway: EstimateGateway,
    val playerGateway: PlayerGateway,
    val questionGateway: QuestionGateway,
) {
    fun execute(lobbyId: LobbyId, questionId: QuestionId): QuestionResults {
        val lobby = lobbyGateway.getById(lobbyId)
        if (lobby.questionId != questionId || lobby.questionExpiresAt == null) {
            throw ValidationException("That question is not associated with this lobby")
        }

        val estimates = estimateGateway.findByLobbyAndQuestion(lobbyId, questionId)
        val players = playerGateway.findParticipatingByLobbyId(lobbyId)
        if (players.size != estimates.size && lobby.questionExpiresAt!!.isAfter(LocalDateTime.now())) {
            throw ValidationException("Cannot view results before all players have had a chance to answer")
        }
        val scores = mapScores(estimates, players)
        val question = questionGateway.getById(questionId)
        val gameOver = lobby.questionCount == lobby.questionLimit
        return QuestionResults(question, gameOver, scores)
    }

    fun mapScores(estimates: List<Estimate>, players: List<Player>): List<Score> {
        val playerMap = players.associateBy { player -> player.id }
        return estimates.map { estimate ->
            val player = playerMap[estimate.playerId]!!
            Score(
                value = estimate.score,
                cumulativeScore = player.score,
                playerName = player.name,
                lowerBound = estimate.lowerBound,
                upperBound = estimate.upperBound
            )
        }
    }
}