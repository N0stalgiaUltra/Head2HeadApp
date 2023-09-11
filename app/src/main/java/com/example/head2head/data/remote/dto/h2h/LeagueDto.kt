package com.example.head2head.data.remote.dto.h2h

/*TODO: Adicionar dados da Liga localmente*/
data class LeagueDto(
    val id : Int?,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
    val season: Int,
    val round: String
)