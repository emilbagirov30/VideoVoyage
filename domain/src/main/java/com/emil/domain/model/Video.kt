package com.emil.domain.model

data class Video (val name:String, val url:String?, val duration:Int)



fun Video.toCacheModel ():VideoCache{
    return VideoCache(name = this.name, duration = this.duration)
}