package com.n0stalgiaultra.head2head.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.n0stalgiaultra.head2head.data.local.dao.TeamDao
import com.n0stalgiaultra.head2head.data.local.model.TeamLocal

@Database(entities = [TeamLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun teamDao(): TeamDao
    companion object{
        const val DATABASE_NAME: String = "h2h-app-db"
    }
}