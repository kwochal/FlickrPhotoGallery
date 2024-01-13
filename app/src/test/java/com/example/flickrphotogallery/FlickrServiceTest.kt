package com.example.flickrphotogallery

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FlickrServiceTest {

    @Test
    fun fetchPublicPhotos_returnsCorrectResponse() = runBlocking {
        val flickrService = mock(FlickrService::class.java)

        val mockResponse = PhotoResponse(items = listOf(Photo("Title", "Link",Media("URL"), "Author", "tag1 tag2")))
        `when`(flickrService.getPublicPhotos()).thenReturn(mockResponse)

        val response = flickrService.getPublicPhotos()

        assertEquals(1, response.items.size)
        assertEquals("Title", response.items[0].title)
        assertEquals("Link", response.items[0].link)
        assertEquals("URL", response.items[0].media.m)
        assertEquals("Author", response.items[0].author)
        assertEquals("tag1 tag2", response.items[0].tags)
    }

}