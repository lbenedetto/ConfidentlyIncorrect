package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.QuestionId
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblEstimate
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EstimateRepository : CrudRepository<TblEstimate, Long> {
    @Query("""SELECT e FROM TblEstimate e
        WHERE e.lobbyId = :lobbyId
        AND e.questionId = :questionId
        ORDER BY e.score DESC
    """)
    fun findAllByLobbyIdAndQuestionId(
        @Param("lobbyId") lobbyId: String,
        @Param("questionId") questionId: Long
    ): List<TblEstimate>

    @Query("""SELECT DISTINCT(e.questionId) FROM TblEstimate e
        WHERE e.lobbyId = :lobbyId
    """)
    fun isQuestionUniqueToLobby(
        @Param("lobbyId") lobbyId: String
    ): List<Long>

    fun findByLobbyIdAndQuestionIdAndPlayerId(
        @Param("lobbyId") lobbyId: String,
        @Param("questionId") questionId: Long,
        @Param("playerId") playerId: Long
    ): Optional<TblEstimate>

}