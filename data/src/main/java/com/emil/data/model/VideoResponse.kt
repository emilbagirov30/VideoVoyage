package com.emil.data.model


import com.emil.domain.model.Video
import com.squareup.moshi.Json


data class VideoResponse (@Json(name = "name") val name:String,
                          @Json(name = "url") val url:String?,
                          @Json(name = "duration") val duration:Int)

fun VideoResponse.toDomainModel ():Video {
    return Video(name = this.name, url = this.url, duration = this.duration)
}

fun List<VideoResponse>.toDomainModelList(): List<Video> {
    return this.map {
        it.toDomainModel()
    }
}