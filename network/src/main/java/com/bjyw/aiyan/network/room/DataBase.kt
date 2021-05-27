package com.bjyw.aiyan.network.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bjyw.aiyan.network.applocation.DataApplication


@Database(entities = [Data::class],exportSchema = false,version = 1)
abstract class DataBase :RoomDatabase() {



    companion object {
        val  dataBase = Room.databaseBuilder(
                DataApplication.baseApplication,
                RoomDao::class.java,
                "student")
                .build()
    }

    abstract fun  getData() : Data
}