package com.example.head2head.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.head2head.data.remote.dto.h2h.GoalsDto
import com.example.head2head.data.remote.dto.h2h.H2HDto
import com.example.head2head.data.remote.response.HTHResponse
import com.example.head2head.domain.H2HRepository
import com.example.head2head.domain.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class H2HViewModel(
    private val repository: H2HRepository
): ViewModel() {

    private val _teamsH2HList = MutableLiveData<List<H2HDto>>()
    val teamsH2HList : LiveData<List<H2HDto>> get() = _teamsH2HList

    /* TODO: Adicionar um remote data source para o H2H*/
    suspend fun getRemoteData(id1: Int, id2: Int) = viewModelScope.launch{
        _teamsH2HList.value = repository.getRemoteData(id1, id2)
    }
//    suspend fun getRemoteData(id1: Int, id2: Int) = withContext(Dispatchers.IO){
//        val teams: String = "${id1}-${id2}"
//        val call: Call<HTHResponse> = api.getH2H(teams)
//        call.enqueue(
//            object: Callback<HTHResponse> {
//                override fun onResponse(
//                    call: Call<HTHResponse>,
//                    response: Response<HTHResponse>
//                ) {
//                    val data = response.body()
//
//                    if(data != null) {
//                        _teamsH2HList.value = data.h2hResponse
//
//                    }
//
//                    Log.d("response", "Success")
//
//                }
//
//                override fun onFailure(call: Call<HTHResponse>, t: Throwable) {
//                    Log.d("response", t.message ?: "")
//                }
//            }
//        )
//    }

    /**
     * Get the count of the wins, loss, and ties from the duel
     *
     * @param id1  Id of the first team
     * @return returns the count of the wins, lost and ties - in that order
     */
    suspend fun getWinnersCount(id1: Int): Triple<Int, Int, Int> = withContext(Dispatchers.IO){
        var count1 = 0
        var count2 = 0
        var ties = 0
        _teamsH2HList.value?.map{
            Log.d("List", "entrei")
            if(it.teams.home.winner == null){
                ties++
            }
            else{
                if(it.teams.home.id == id1){
                    if(it.teams.home.winner == true)
                    {
                        count1++
                    }
                    else {
                        count2++
                    }
                } else {
                    if(it.teams.home.winner == true){
                        count2++
                    }
                    else
                    {
                        count1++
                    }
                }


            }

        }

        return@withContext Triple(count1, count2, ties)
    }

}

