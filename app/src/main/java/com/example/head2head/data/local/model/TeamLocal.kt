package com.example.head2head.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val code: String,
    val logo: String
)
