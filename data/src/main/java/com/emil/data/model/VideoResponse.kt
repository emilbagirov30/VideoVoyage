package com.emil.data.model

import com.emil.domain.model.Video

data class VideoResponse (val name:String, val url:String, val duration:Int)

fun VideoResponse.toDomainModel ():Video {
    return Video(name = this.name, url = this.url, duration = this.duration)
}

fun List<VideoResponse>.toDomainModelList(): List<Video> {
    return this.map {
        it.toDomainModel()
    }
}