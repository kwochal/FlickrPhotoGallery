package com.example.flickrphotogallery

import retrofit2.http.GET

data class PhotoResponse(
    val items: List<Photo>
)
interface FlickrService {
    @GET("photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPublicPhotos(): PhotoResponse
}

