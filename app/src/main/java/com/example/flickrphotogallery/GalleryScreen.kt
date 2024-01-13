package com.example.flickrphotogallery

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel ) {
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(viewModel.isRefreshing, {
        viewModel.isRefreshing=true
        viewModel.fetchPublicPhotos()
        Toast.makeText(context, "Odświeżono", Toast.LENGTH_SHORT).show()
        viewModel.isRefreshing=false})
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pullRefresh(pullRefreshState)
    ) {
        val photos by rememberUpdatedState(newValue = viewModel.photos.value)

        LaunchedEffect(true) {
            if (photos.isEmpty()) {
                viewModel.fetchPublicPhotos()
            }
        }
        LazyColumn {
            items(photos) { photo ->
                PhotoItem(photo = photo)
            }
        }
    }
    PullRefreshIndicator(
        refreshing = viewModel.isRefreshing,
        state = pullRefreshState
    )
}

@Composable
fun PhotoItem(photo: Photo) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = photo.media.m),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )
            if(photo.title!="") {
                Text(
                    text = photo.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
            }
            Text(
                text = "author: ${photo.getCleanAuthor()}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            if(photo.tags!="") {
                Text(
                    text = "tags: "+photo.tags,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}

