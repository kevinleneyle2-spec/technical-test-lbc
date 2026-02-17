package fr.leboncoin.androidrecruitmenttestapp.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.adevinta.spark.components.chips.ChipTinted
import fr.leboncoin.data.network.model.AlbumDto


@Composable
fun DetailsBody(
    albumDto: AlbumDto?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(albumDto?.url)
                .httpHeaders(
                    NetworkHeaders.Builder()
                        .add("User-Agent", "LeboncoinApp/1.0")
                        .build()
                )
                .crossfade(true)
                .build(),
            contentDescription = albumDto?.title,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        albumDto?.title?.let {
            Text(
                text = it,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    all = 18.dp
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
        Spacer(Modifier.weight(2f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(64.dp),
        ) {
            ChipTinted(text = "Album #${albumDto?.albumId}")
            ChipTinted(text = "Track #${albumDto?.id}")
        }
        DetailsFavoriteButton(
            onClick = {onFavoriteClick()},
            isFavorite = isFavorite
        )
    }

}