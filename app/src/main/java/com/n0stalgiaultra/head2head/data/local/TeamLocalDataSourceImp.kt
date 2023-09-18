package com.n0stalgiaultra.head2head.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.n0stalgiaultra.head2head.data.local.dao.TeamDao
import com.n0stalgiaultra.head2head.data.local.model.TeamLocal
import com.n0stalgiaultra.head2head.data.remote.dto.TeamDto
import com.n0stalgiaultra.head2head.domain.team.TeamLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamLocalDataSourceImp (val dao: TeamDao): TeamLocalDataSource {
    override fun insert(team: List<TeamDto>) {
        CoroutineScope(Dispatchers.IO).launch {
            for(teamData in team){
                val existingTeam = dao.getTeamById(teamData.id)
                if(existingTeam == null)
                    dao.insert(convertToLocal(team))
            }
        }
        Log.d("db", "items inserted")
    }

    override fun getTeam(): LiveData<List<TeamLocal>> {
        return dao.getTeams()
    }

    private fun convertToLocal(team: List<TeamDto>): List<TeamLocal>{
        return team.map {
            TeamLocal(
                id = it.id,
                name = it.name,
                code = it.code,
                logo = it.logo
            )
        }
    }
}