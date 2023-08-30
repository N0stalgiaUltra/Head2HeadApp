package com.example.head2head.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.head2head.data.remote.FootballAPI
import com.example.head2head.data.remote.response.TeamResponse
import com.example.head2head.domain.TeamCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val api: FootballAPI): ViewModel() {

    private val _teamCardList = MutableLiveData<List<TeamCard>>()
    val teamCardList: LiveData<List<TeamCard>> get() = _teamCardList

    suspend fun getAllTeams() = withContext(Dispatchers.IO){
        val call: Call<TeamResponse> = api.getTeams()
        call.enqueue(
            object: Callback<TeamResponse>{
                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    Log.d("response", "Successful")
                    Log.d("response", response.body().toString())
                    _teamCardList.value = response.body()?.teamResponse?.map {
                        it.team.toTeamCard()
                    }


                }

                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                    Log.d("response", t.message ?: "")
                }
            }
        )
    }
}