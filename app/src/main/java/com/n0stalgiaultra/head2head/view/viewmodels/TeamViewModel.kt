package com.n0stalgiaultra.head2head.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.n0stalgiaultra.head2head.data.local.model.TeamLocal
import com.n0stalgiaultra.head2head.domain.mapper.team.TeamCard
import com.n0stalgiaultra.head2head.domain.mapper.team.TeamItem
import com.n0stalgiaultra.head2head.domain.team.TeamRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(
    private val repository: TeamRepository
): ViewModel() {

    private val _teamList = MutableLiveData<List<TeamLocal>?>()
    val teamList: LiveData<List<TeamLocal>?> get() = _teamList

    private val _teamCardList = MutableLiveData<List<TeamCard>>()
    val teamCardList: LiveData<List<TeamCard>> get() = _teamCardList

    private val _teamItemList = MutableLiveData<List<TeamItem>>()
    val teamItemList: LiveData<List<TeamItem>> get() = _teamItemList


    fun getTeamsLocal(){
        repository.getLocalTeams().observeForever{
                localData ->
                    if(localData.isNullOrEmpty()){
                        CoroutineScope(Dispatchers.IO).launch {
                            Log.d("local", "Empty List, Trying API")
                            repository.getRemoteTeams()
                        }
                    }
                    else{
                        _teamList.value = localData
                        mapTeamItems()
                        Log.d("Local", "Success")
                    }

        }
    }

    private fun mapTeamItems(){
        _teamCardList.value = _teamList.value!!.map { it.toTeamCard() }
        _teamItemList.value = _teamList.value!!.map { it.toTeamItem() }
    }

    fun getTeamCard(id: Int): TeamCard?{
        val team = _teamCardList.value?.find { it.teamId == id }
        return team
    }

}