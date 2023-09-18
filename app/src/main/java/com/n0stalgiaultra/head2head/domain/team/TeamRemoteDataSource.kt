package com.n0stalgiaultra.head2head.domain.team

import com.n0stalgiaultra.head2head.data.remote.dto.TeamDto

interface TeamRemoteDataSource {
    suspend fun getRemoteTeams(): List<TeamDto>
}