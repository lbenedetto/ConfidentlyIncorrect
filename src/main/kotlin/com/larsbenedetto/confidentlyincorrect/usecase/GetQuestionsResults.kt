package com.larsbenedetto.confidentlyincorrect.usecase

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.domain.Score
import com.larsbenedetto.confidentlyincorrect.domain.entity.Estimate
import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.entity.Team
import com.larsbenedetto.confidentlyincorrect.gateway.*
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
  val teamGateway: TeamGateway,
) {
  fun execute(lobbyId: LobbyId, questionId: QuestionId): QuestionResults {
    val lobby = lobbyGateway.getById(lobbyId)
    if (lobby.questionId != questionId || lobby.questionExpiresAt == null) {
      throw ValidationException("That question is not associated with this lobby")
    }

    val playerEstimates = estimateGateway.findPlayerEstimatesByLobbyAndQuestion(lobbyId, questionId)
    val players = playerGateway.findParticipatingByLobbyId(lobbyId)
    if (players.size != playerEstimates.size && lobby.questionExpiresAt!!.isAfter(LocalDateTime.now())) {
      throw ValidationException("Cannot view results before all players have had a chance to answer")
    }
    val teams = teamGateway.findByLobbyId(lobby.id)
    val question = questionGateway.getById(questionId)
    val teamEstimates = estimateGateway.findTeamEstimatesByLobbyAndQuestion(lobbyId, questionId)

    val allScores = mapScores(playerEstimates + teamEstimates, players, teams)
    val scores = allScores
      .filter { it.isPlayer || it.teamId != lobby.defaultTeamId }
      .sortedWith(compareBy({ it.teamId.value }, { it.isPlayer }))
    val lobbyScore = allScores.find { !it.isPlayer && it.teamId == lobby.defaultTeamId }!!
    val gameOver = lobby.questionCount == lobby.questionLimit
    return QuestionResults(question, gameOver, scores, lobbyScore)
  }

  fun mapScores(
    estimates: List<Estimate>,
    players: List<Player>,
    teams: List<Team>
  ): List<Score> {
    val playerMap = players.associateBy { player -> player.id }
    val teamMap = teams.associateBy { team -> team.id }
    return estimates.map { estimate ->
      val player = estimate.playerId?.let { playerMap[it] }
      val team = teamMap[estimate.teamId]!!
      Score(
        value = estimate.score,
        cumulativeScore = player?.score ?: team.score,
        scorerName = player?.name ?: team.name,
        teamId = player?.teamId ?: team.id!!,
        isPlayer = player != null,
        lowerBound = estimate.lowerBound,
        upperBound = estimate.upperBound
      )
    }
  }
}
