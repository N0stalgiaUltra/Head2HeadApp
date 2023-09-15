package com.example.head2head.domain.team

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.head2head.data.local.model.TeamLocal
import com.example.head2head.data.remote.FootballAPI
import com.example.head2head.data.remote.dto.TeamDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamRepositoryImpl(val remote: TeamRemoteDataSource,
                         val local: TeamLocalDataSource): TeamRepository {

    override fun getLocalTeams(): LiveData<List<TeamLocal>> {
        return local.getTeam()
    }

    override suspend fun getRemoteTeams(): List<TeamLocal> {
        CoroutineScope(Dispatchers.IO).launch{
             val obj = remote.getRemoteTeams()
             local.insert(obj)
        }
        return local.getTeam().value!!
    }
}