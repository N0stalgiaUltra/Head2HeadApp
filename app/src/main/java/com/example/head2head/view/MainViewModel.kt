package com.example.head2head.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.head2head.data.local.model.TeamLocal
import com.example.head2head.data.remote.FootballAPI
import com.example.head2head.data.remote.response.TeamResponse
import com.example.head2head.domain.TeamLocalDataSource
import com.example.head2head.domain.mapper.TeamCard
import com.example.head2head.domain.mapper.TeamItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    val api: FootballAPI,
    val local: TeamLocalDataSource
): ViewModel() {

    private val _teamList = MutableLiveData<List<TeamLocal>?>()
    val teamList: LiveData<List<TeamLocal>?> get() = _teamList

    private val _teamCardList = MutableLiveData<List<TeamCard>>()
    val teamCardList: LiveData<List<TeamCard>> get() = _teamCardList

    private val _teamItemList = MutableLiveData<List<TeamItem>>()
    val teamItemList: LiveData<List<TeamItem>> get() = _teamItemList

    /*TODO: Fazer o Mapping dos dados*/
    /*TODO: Recuperar os dados de H2H*/
    fun getTeamsLocal(){

        local.getTeam().observeForever{
            localData ->
            if(localData.isNullOrEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("local", "Empty List, Trying API")
                    getTeamsRemote()
                }
            }
            else{
                _teamList.value = localData
                Log.d("Local", "Success")
                Log.d("local", "${_teamList.value!!.size}")
            }

        }
    }
    suspend fun getTeamsRemote() = withContext(Dispatchers.IO){
        val call: Call<TeamResponse> = api.getTeams()
        call.enqueue(
            object: Callback<TeamResponse>{
                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    val team = response.body()
                    if(team != null) {
                        local.insert(team.teamResponse.map { it.team })
                        _teamList.value = local.getTeam().value
                    }
//                    _teamCardList.value = response.body()?.teamResponse?.map {
//                        it.team.toTeamCard()
//                    }
//                    _teamItemList.value = response.body()?.teamResponse?.map {
//                        it.team.toTeamItem()
//                    }
                    Log.d("response", "Success")

                }

                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                    Log.d("response", t.message ?: "")
                }
            }
        )
    }
}