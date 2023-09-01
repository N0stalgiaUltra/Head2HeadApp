package com.example.head2head.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.head2head.data.local.model.TeamLocal


@Dao
interface TeamDao {
    //Faz as operações no DB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team: List<TeamLocal>)

    @Query("SELECT * FROM teamlocal")
    fun getTeams() : LiveData<List<TeamLocal>>

    @Query("SELECT * FROM teamLocal WHERE id = :teamId")
    fun getTeamById(teamId : Int): TeamLocal?
}