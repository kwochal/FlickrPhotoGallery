package com.example.flickrphotogallery

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel(private var flickrService : FlickrService) : ViewModel() {

    val photos = mutableStateOf<List<Photo>>(emptyList())
    var isRefreshing = false

    companion object {
        fun createFactory(flickrService: FlickrService): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(flickrService) as T
                }
            }
        }
    }

    fun fetchPublicPhotos() {
        viewModelScope.launch {
            val response = flickrService.getPublicPhotos()
            photos.value = response.items
        }
    }


}
