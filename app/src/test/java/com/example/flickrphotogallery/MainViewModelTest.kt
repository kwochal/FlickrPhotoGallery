package com.example.flickrphotogallery

import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Default)
        viewModel = MainViewModel()
    }

    @Test
    fun testFetchPublicPhotos_Success() = runBlocking {
        val flickrService = Mockito.mock(FlickrService::class.java)
        viewModel.setFlickrService(flickrService)
        val mockResponse = PhotoResponse(items = listOf(Photo("Title", "Link",Media("URL"), "Author", "tag1 tag2")))
        Mockito.`when`(flickrService.getPublicPhotos()).thenReturn(mockResponse)

        viewModel.fetchPublicPhotos()
        delay(100)
        val response = viewModel.photos

        assertEquals(1, response.value.size)
        assertEquals("Title", response.value[0].title)
        assertEquals("Link", response.value[0].link)
        assertEquals("URL", response.value[0].media.m)
        assertEquals("Author", response.value[0].author)
        assertEquals("tag1 tag2", response.value[0].tags)
    }

}