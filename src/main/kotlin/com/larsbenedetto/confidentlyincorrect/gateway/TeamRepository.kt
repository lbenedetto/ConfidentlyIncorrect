package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.gateway.model.TblTeam
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : CrudRepository<TblTeam, Long> {

    @Query("""SELECT t FROM TblTeam t
        WHERE t.lobbyId = :lobbyId
        ORDER BY t.score DESC
    """)
    fun findByLobbyId(@Param("lobbyId") lobbyId: String): List<TblTeam>
}
