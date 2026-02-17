package fr.leboncoin.androidrecruitmenttestapp.home.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adevinta.spark.components.scaffold.Scaffold
import fr.leboncoin.androidrecruitmenttestapp.R
import fr.leboncoin.data.local.model.FavoriteAlbumDto
import fr.leboncoin.data.network.model.AlbumDto

@Composable
fun HomeScreen(
    albums: List<AlbumDto>,
    favoriteList: List<FavoriteAlbumDto>?,
    onItemSelected: (AlbumDto) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "loader-rotation")
                val angle by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing)
                    ),
                    label = "loader-angle"
                )
                Image(
                    painter = painterResource(id = R.drawable.img_loader),
                    contentDescription = "Image Button",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(angle)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = paddingValues,
            ) {
                items(
                    items = albums,
                    key = { album -> album.id }
                ) { album ->
                    HomeAlbumItem(
                        album = album,
                        isFavorite = favoriteList?.find { it.id == album.id } != null,
                        onItemSelected = onItemSelected,
                    )
                }
            }
        }
    }
}
