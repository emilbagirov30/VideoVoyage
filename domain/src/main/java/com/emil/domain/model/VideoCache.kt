package com.emil.domain.model

data class VideoCache (val name:String, val duration:Int)



fun VideoCache.toResponseModel():Video {
    return Video(name = this.name,url = null, duration = this.duration)
}


fun List<VideoCache>.toResponseModelList():List<Video> {
    return this.map { it.toResponseModel() }
}