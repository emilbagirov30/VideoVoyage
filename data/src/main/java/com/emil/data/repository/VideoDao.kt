package com.emil.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emil.data.model.VideoLocalDb

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheVideo(video:VideoLocalDb)

    @Query("SELECT * FROM video")
    suspend fun getAllVideo(): List<VideoLocalDb>

}