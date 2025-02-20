package com.emil.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emil.data.model.VideoLocalDb

@Database(entities = [VideoLocalDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}
