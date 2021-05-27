package com.bjyw.aiyan.network.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Data (
        @PrimaryKey(autoGenerate = true) var id : Int,
        @ColumnInfo(name = "name") var name : String,
        @ColumnInfo(name = "sex") var sex : String
)