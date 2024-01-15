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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(viewModel.isRefreshing, {
        viewModel.fetchPublicPhotos()
        Toast.makeText(context, R.string.refreshed, Toast.LENGTH_SHORT).show()
        })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium_padding))
            .pullRefresh(pullRefreshState)
    ) {
        val photos = viewModel.photos.value
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
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.small_padding)),
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
            if(!photo.title.isNullOrEmpty()) {
                Text(
                    text = photo.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small_padding)),
                )
            }
            Text(
                text = context.getString(R.string.author, photo.getCleanAuthor()),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.small_padding))
            )
            if(!photo.tags.isNullOrEmpty()) {
                Text(
                    text = context.getString(R.string.tags, photo.tags),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small_padding))
                )
            }
        }
    }
}

