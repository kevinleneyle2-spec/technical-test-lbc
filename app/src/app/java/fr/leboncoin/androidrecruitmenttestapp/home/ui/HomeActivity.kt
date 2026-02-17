package fr.leboncoin.androidrecruitmenttestapp.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.adevinta.spark.SparkTheme
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.androidrecruitmenttestapp.navigation.AppDestinations
import fr.leboncoin.androidrecruitmenttestapp.navigation.AppNavHost
import fr.leboncoin.data.utils.AnalyticsHelper
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        analyticsHelper.initialize(this)

        setContent {
            SparkTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    startDestination = AppDestinations.HOME_ROUTE
                )
            }
        }
    }
}