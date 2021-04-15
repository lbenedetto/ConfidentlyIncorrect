package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.gateway.model.TblPlayer
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : CrudRepository<TblPlayer, Long> {

    @Query(
        "SELECT p FROM TblPlayer p" +
                " WHERE p.lobbyId = :lobbyId" +
                " AND p.isParticipating = true" +
                " ORDER BY p.score DESC"
    )
    fun findParticipatingByLobbyId(@Param("lobbyId") lobbyId: String): List<TblPlayer>

    @Query(
        "SELECT p FROM TblPlayer p" +
                " WHERE p.lobbyId = :lobbyId" +
                " ORDER BY p.score DESC"
    )
    fun findByLobbyId(lobbyId: String): List<TblPlayer>
}