package com.example.mvvmsampleapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote (
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val quote:String,
    val author:String,
    val thumbnail:String,
    var created_at:String?,
    var updated_at:String?

)