package fr.leboncoin.androidrecruitmenttestapp.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.leboncoin.androidrecruitmenttestapp.details.viewmodel.DetailsViewModel
import fr.leboncoin.data.network.model.AlbumDto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    album: AlbumDto?,
    onBackPressed: () -> Unit,
    onFavoriteClick: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val error by viewModel.error.collectAsState()

    error?.let {
        DetailsErrorDialog(
            errorMessage = it.localizedMessage ?: "Une erreur inattendue est survenue.",
            onDismiss = { viewModel.dismissErrorDialog() }
        )
    }

    LaunchedEffect(album) {
        album?.let {
            viewModel.checkFavoriteStatus(album)
        }
    }

    val isFavorite by viewModel.isFavorite.collectAsState()

    Scaffold(topBar = { DetailsTopBar(onBackPressed = onBackPressed) }, modifier = modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = it,
        ) {
            item {
                DetailsBody(albumDto = album, isFavorite, onFavoriteClick)
            }
        }
    }
}