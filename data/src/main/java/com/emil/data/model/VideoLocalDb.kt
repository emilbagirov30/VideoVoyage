package com.emil.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

import com.emil.domain.model.VideoCache


@Entity(tableName = "video",
    indices = [Index(value = ["name"], unique = true)])
data class VideoLocalDb (@PrimaryKey(autoGenerate = true) val id:Long,
                         val name:String,
                         val duration:Int)

fun VideoLocalDb.toDomainModel():VideoCache{
    return VideoCache(name = this.name, duration = this.duration)
}


fun List<VideoLocalDb>.toDomainModelList():List<VideoCache>{
    return  this.map {
        it.toDomainModel()
    }
}


fun VideoCache.toLocalDbModel():VideoLocalDb{
    return VideoLocalDb(id = 0, name = this.name, duration = this.duration)
}