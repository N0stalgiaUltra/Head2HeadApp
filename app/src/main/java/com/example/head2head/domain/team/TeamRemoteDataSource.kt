package com.example.head2head.domain.team

import com.example.head2head.data.remote.dto.TeamDto

interface TeamRemoteDataSource {
    suspend fun getRemoteTeams(): List<TeamDto>
}