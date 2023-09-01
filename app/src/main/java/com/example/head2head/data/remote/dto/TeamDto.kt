package com.example.head2head.data.remote.dto

import com.example.head2head.domain.mapper.TeamCard
import com.example.head2head.domain.mapper.TeamItem

data class TeamDto(
    val id: Int,
    val name: String,
    val code: String,
    val logo: String
){
    fun toTeamCard(): TeamCard {
        return TeamCard(
            teamImage = logo,
            teamAbvr = code
        )
    }

    fun toTeamItem(): TeamItem {
        return TeamItem(
            teamImage = logo,
            teamName = name
        )
    }
}
