package com.example.head2head.data.remote.dto.h2h

data class TeamsDto(
    val home: HomeTeamDto,
    val away: AwayTeamDto
)

data class HomeTeamDto(
    val id: Int,
    val winner: Boolean?
)

data class AwayTeamDto(
    val id: Int,
    val winner: Boolean?
)
