package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.database.Lobby
import com.larsbenedetto.confidentlyincorrect.domain.database.Player
import com.larsbenedetto.confidentlyincorrect.domain.identity.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.identity.PlayerId
import com.larsbenedetto.confidentlyincorrect.domain.identity.StringIdentity
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface PlayerRepository : CrudRepository<Player, PlayerId> {

    @Query("SELECT p FROM Player p" +
                    " WHERE p.lobbyId = :lobbyId" +
                    " AND p.isParticipating = true")
    fun findParticipatingByLobbyId(@Param("lobbyId") lobbyId: LobbyId) : List<Player>
}

@Service
class PlayerGateway(
    val playerRepository: PlayerRepository

) {
    fun createPlayer(name: String, lobbyId: LobbyId, isParticipating: Boolean = true): Player {
        val playerId = PlayerId(StringIdentity.generateIdValue(25))
        return playerRepository.save(
            Player(
                id = playerId,
                name = name,
                score = 0.0,
                lobbyId = lobbyId,
                isParticipating = isParticipating
            )
        )
    }

    fun findParticipatingByLobbyId(lobbyId: LobbyId) : List<Player> {
        return playerRepository.findParticipatingByLobbyId(lobbyId)
    }

    fun save(player: Player) {
        playerRepository.save(player)
    }

    fun getById(id: PlayerId): Player {
        return playerRepository.findById(id)
            .orElseThrow { EntityLookupFailedException(Player::class.simpleName, id) }
    }
}