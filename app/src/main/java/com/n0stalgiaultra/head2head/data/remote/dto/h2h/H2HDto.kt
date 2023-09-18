package com.n0stalgiaultra.head2head.data.remote.dto.h2h

data class H2HDto(
    val fixture : FixtureDto,
    val league: LeagueDto,
    val teams: TeamsDto,
    val goals: GoalsDto
)