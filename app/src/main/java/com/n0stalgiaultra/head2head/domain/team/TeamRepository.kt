package com.n0stalgiaultra.head2head.domain.team

import androidx.lifecycle.LiveData
import com.n0stalgiaultra.head2head.data.local.model.TeamLocal

interface TeamRepository {
    fun getLocalTeams(): LiveData<List<TeamLocal>>
    suspend fun getRemoteTeams(): LiveData<List<TeamLocal>>
}