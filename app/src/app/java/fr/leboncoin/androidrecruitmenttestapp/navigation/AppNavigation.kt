package fr.leboncoin.androidrecruitmenttestapp.navigation

object AppDestinations {
    const val HOME_ROUTE = "home"
    const val DETAILS_ROUTE = "details/{albumId}"
    fun buildDetailsRoute(albumId: Int) = "details/$albumId"
}