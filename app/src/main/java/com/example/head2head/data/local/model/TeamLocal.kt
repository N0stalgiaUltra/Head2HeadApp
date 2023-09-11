package com.example.head2head.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.head2head.domain.mapper.team.TeamCard
import com.example.head2head.domain.mapper.team.TeamItem

@Entity
data class TeamLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val code: String,
    val logo: String
){
    fun toTeamCard(): TeamCard {
        return TeamCard(
            teamId = id,
            teamImage = logo,
            teamAbvr = code
        )
    }

    fun toTeamItem(): TeamItem {
        return TeamItem(
            teamId = id,
            teamImage = logo,
            teamName = name
        )
    }
}