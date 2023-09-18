package com.n0stalgiaultra.head2head.domain.team

import android.util.Log
import androidx.lifecycle.LiveData
import com.n0stalgiaultra.head2head.data.local.model.TeamLocal

class TeamRepositoryImpl(val remote: TeamRemoteDataSource,
                         val local: TeamLocalDataSource): TeamRepository {

    override fun getLocalTeams(): LiveData<List<TeamLocal>> {
        return local.getTeam()
    }

    override suspend fun getRemoteTeams(): LiveData<List<TeamLocal>> {
        //val teams : List<TeamLocal>
        try {
            val response = remote.getRemoteTeams()
            local.insert(response)
        } catch (e: Exception){
            Log.e("Remote Teams", "Error ${e.message}", e)
        }
        return local.getTeam()

    }
}