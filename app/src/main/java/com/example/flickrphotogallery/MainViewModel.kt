package com.example.flickrphotogallery

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.flickr.com/services/feeds/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var flickrService : FlickrService = retrofit.create(FlickrService::class.java)

    private val _photos = mutableStateOf<List<Photo>>(emptyList())
    val photos: State<List<Photo>> = _photos
    var isRefreshing = false

    fun fetchPublicPhotos() {
        viewModelScope.launch {
            val response = flickrService.getPublicPhotos()
            _photos.value = response.items
        }
    }

    fun setFlickrService(newService : FlickrService) {
        flickrService = newService;
    }

}
