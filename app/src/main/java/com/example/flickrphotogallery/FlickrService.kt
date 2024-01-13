package com.example.flickrphotogallery

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Query

import retrofit2.Call
import retrofit2.http.GET

const val AUTHOR_PREFIX = "nobody@flickr.com"

data class Photo(
    val title: String,
    val link: String,
    val media: Media,
    val author: String,
    val tags: String
) {
    fun getCleanAuthor() : String {
        val noPrefix = author.substringAfter(AUTHOR_PREFIX)
        val result = noPrefix.replace("(","").replace(")","").replace("\"","")
        return result
    }
}

data class Media(
    val m: String
)

data class PhotoResponse(
    val items: List<Photo>
)

interface FlickrService {
    @GET("photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPublicPhotos(): PhotoResponse
}

