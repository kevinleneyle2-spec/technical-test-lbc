package fr.leboncoin.androidrecruitmenttestapp.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.adevinta.spark.components.text.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Album Details")
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                },
                Modifier.testTag("detailsBackButton")
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "menu items"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
    )
}