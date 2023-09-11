package com.example.head2head.data.remote.dto

import com.example.head2head.domain.mapper.team.TeamCard
import com.example.head2head.domain.mapper.team.TeamItem

data class TeamDto(
    val id: Int,
    val name: String,
    val code: String,
    val logo: String
)
