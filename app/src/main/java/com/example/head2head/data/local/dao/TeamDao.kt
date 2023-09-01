package com.example.head2head.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.head2head.data.local.model.TeamLocal
import com.example.head2head.data.remote.response.TeamResponse
import com.example.head2head.data.remote.response.TeamResponseItem


@Dao
interface TeamDao {
    //Faz as operações no DB

    @Insert
    fun insert(team: List<TeamLocal>)

    @Query("SELECT * FROM teamlocal")
    fun getTeam() : LiveData<List<TeamLocal>>
}