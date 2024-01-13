package com.example.flickrphotogallery

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PhotoTest {

    private fun getPhotos(): List<Photo> = listOf(
        Photo("title1", "link1", Media(m = "media1"), "nobody@flickr.com(\"adamnowak\")", "tag tag"),
        Photo("title2", "link2", Media(m = "media2"), "nobody@flickr.com(\"pe@rl77\")", "tag1 tag2")
    )

    @Test
    fun getCleanAuthor_PrefixSuffixRemoved() {
        val photos = getPhotos()
        assertEquals("adamnowak", photos[0].getCleanAuthor())
        assertEquals("pe@rl77", photos[1].getCleanAuthor())
    }
    @Test
    fun getCleanAuthor_NoChangeNeeded() {
        val photo = Photo("title1", "link1", Media(m = "media1"), "monk@y1", "tag tag")
        assertEquals("monk@y1", photo.getCleanAuthor())
    }

}