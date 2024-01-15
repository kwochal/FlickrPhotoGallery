package com.example.flickrphotogallery

data class Photo(
    val title: String,
    val link: String,
    val media: Media,
    val author: String,
    val tags: String
) {
    fun getCleanAuthor(): String {
        val authorPrefix = "nobody@flickr.com"
        return author
            .substringAfter(authorPrefix)
            .replace(Regex("[\"()]"), "")
    }
}
data class Media(
    val m: String
)