package com.n0stalgiaultra.head2head.domain.team

import androidx.lifecycle.LiveData
import com.n0stalgiaultra.head2head.data.local.model.TeamLocal
import com.n0stalgiaultra.head2head.data.remote.dto.TeamDto

interface TeamLocalDataSource {

    fun insert(team: List<TeamDto>)
    fun getTeam() : LiveData<List<TeamLocal>>

}