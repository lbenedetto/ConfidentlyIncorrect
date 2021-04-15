package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.entity.Player
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.PlayerId
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblPlayer
import com.larsbenedetto.confidentlyincorrect.util.mapNull
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.stereotype.Service

@Service
class PlayerGateway(
    val playerRepository: PlayerRepository
) {
    fun findParticipatingByLobbyId(lobbyId: LobbyId): List<Player> {
        return playerRepository.findParticipatingByLobbyId(lobbyId.value)
            .map(this::toEntity)
    }

    fun findByLobbyId(lobbyId: LobbyId): List<Player> {
        return playerRepository.findByLobbyId(lobbyId.value)
            .map(this::toEntity)
    }

    fun save(player: Player): Player {
        val tbl = playerRepository.save(fromEntity(player))
        return toEntity(tbl)
    }

    fun getById(id: PlayerId): Player {
        return playerRepository.findById(id.value)
            .map(this::toEntity)
            .orElseThrow { EntityLookupFailedException(Player::class.simpleName, id) }
    }

    fun fromEntity(entity: Player): TblPlayer {
        return TblPlayer(
            id = entity.id?.value,
            name = entity.name,
            score = entity.score,
            lobbyId = entity.lobbyId?.value,
            isParticipating = entity.isParticipating
        )
    }

    fun toEntity(tbl: TblPlayer): Player {
        return Player(
            id = PlayerId(tbl.id!!),
            name = tbl.name,
            score = tbl.score,
            lobbyId = tbl.lobbyId.mapNull { LobbyId(it) },
            isParticipating = tbl.isParticipating
        )
    }
}