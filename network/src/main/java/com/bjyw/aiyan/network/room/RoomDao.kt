package com.bjyw.aiyan.network.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class RoomDao : RoomDatabase() {

    @Insert
    abstract fun insert(dao:  LiveData<List<Data>>)

    @Delete
    abstract fun deltet(data : Data)

    @Update
    abstract fun update(data: Data)

    @Query("select * from Data")
    abstract fun getDataList() : LiveData<List<Data>>;
}