package fr.leboncoin.androidrecruitmenttestapp.details.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.leboncoin.androidrecruitmenttestapp.R

@SuppressLint("MaterialComposableUsageDetector")
@Composable
fun DetailsFavoriteButton(
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    val imageResource = if (isFavorite) {
        R.drawable.img_pink_heart
    } else {
        R.drawable.img_black_heart
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(64.dp),
    ) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .size(100.dp)
                .testTag(if (isFavorite) "detailsFavoriteButton_on" else "detailsFavoriteButton_off"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.White
            )
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Image Button",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}