package com.n0stalgiaultra.head2head.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n0stalgiaultra.head2head.data.remote.dto.h2h.H2HDto
import com.n0stalgiaultra.head2head.domain.h2h.H2HRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class H2HViewModel(
    private val repository: H2HRepository
): ViewModel() {

    private val _teamsH2HList = MutableLiveData<List<H2HDto>>()
    val teamsH2HList : LiveData<List<H2HDto>> get() = _teamsH2HList

    suspend fun getRemoteData(id1: Int, id2: Int) = viewModelScope.launch{
        _teamsH2HList.value = repository.getRemoteData(id1, id2)
    }

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

