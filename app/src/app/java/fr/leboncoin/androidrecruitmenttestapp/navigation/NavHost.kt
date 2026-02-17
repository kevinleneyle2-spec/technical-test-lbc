package fr.leboncoin.androidrecruitmenttestapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.leboncoin.androidrecruitmenttestapp.details.ui.DetailsScreen
import fr.leboncoin.androidrecruitmenttestapp.details.viewmodel.DetailsViewModel
import fr.leboncoin.androidrecruitmenttestapp.home.ui.HomeScreen
import fr.leboncoin.androidrecruitmenttestapp.home.viewmodel.HomeViewModel
import fr.leboncoin.data.network.model.AlbumDto

@Composable
fun AppNavHost(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = AppDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = AppDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = hiltViewModel()

            val albums by homeViewModel.albums.collectAsState()
            val favoriteList by homeViewModel.favoriteAlbums.collectAsState()
            val isLoading by homeViewModel.isLoading.collectAsState()

            homeViewModel.findAllFavoriteAlbums()

            HomeScreen(
                albums = albums,
                favoriteList = favoriteList,
                isLoading = isLoading,
                onItemSelected = { selectedAlbum ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("album", selectedAlbum)
                    navController.navigate(AppDestinations.DETAILS_ROUTE)
                }
            )
        }
        composable(
            route = AppDestinations.DETAILS_ROUTE,
        ) { backStackEntry ->
            val detailsViewModel: DetailsViewModel = hiltViewModel()

            val album = navController.previousBackStackEntry?.savedStateHandle?.get<AlbumDto>("album")

            val albumId = backStackEntry.arguments?.getInt("albumId")

            DetailsScreen(
                viewModel = detailsViewModel,
                album = album,
                onBackPressed = { navController.navigateUp() },
                onFavoriteClick = {
                    album?.let {
                        detailsViewModel.onFavoriteClick(album)
                    }
                },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
