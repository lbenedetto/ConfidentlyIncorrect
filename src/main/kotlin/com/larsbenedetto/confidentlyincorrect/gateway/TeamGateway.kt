package com.larsbenedetto.confidentlyincorrect.gateway

import com.larsbenedetto.confidentlyincorrect.domain.entity.Team
import com.larsbenedetto.confidentlyincorrect.domain.LobbyId
import com.larsbenedetto.confidentlyincorrect.domain.TeamId
import com.larsbenedetto.confidentlyincorrect.gateway.model.TblTeam
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import org.springframework.stereotype.Service

@Service
class TeamGateway(
    val teamRepository: TeamRepository
) {
    fun findByLobbyId(lobbyId: LobbyId): List<Team> {
        return teamRepository.findByLobbyId(lobbyId.value)
            .map(this::toEntity)
    }

    fun save(team: Team): Team {
        val tbl = teamRepository.save(fromEntity(team))
        return toEntity(tbl)
    }

    fun getById(id: TeamId): Team {
        return teamRepository.findById(id.value)
            .map(this::toEntity)
            .orElseThrow { EntityLookupFailedException(Team::class.simpleName, id) }
    }

    fun fromEntity(entity: Team): TblTeam {
        return TblTeam(
            id = entity.id?.value,
            name = entity.name,
            score = entity.score,
            lobbyId = entity.lobbyId.value
        )
    }

    fun toEntity(tbl: TblTeam): Team {
        return Team(
            id = TeamId(tbl.id!!),
            name = tbl.name,
            score = tbl.score,
            lobbyId = LobbyId(tbl.lobbyId)
        )
    }
}
