package com.example.respfitprototype.ui.exercises
import android.webkit.WebView
import androidx.compose.ui.unit.dp

import android.content.res.Configuration
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.R
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.theme.AppTheme
import com.example.respfitprototype.ui.theme.spacing
import com.example.respfitprototype.ui.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExercisesScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    ScrollableColumn(modifier = Modifier.fillMaxSize()) {
//        Row(modifier = Modifier.padding(5.dp)){
//            VideoPlayer("_KGEkwSRoF0")
//        }
        Row(modifier = Modifier.padding(5.dp)){
            VideoPlayer("3FGL2GpA904")
        }
        Row(modifier = Modifier.padding(5.dp)){
            VideoPlayer("Nt3Qh_oJ3YY")
        }
        Row(modifier = Modifier.padding(5.dp)){
            VideoPlayer("ssss7V1_eyA")
        }
        Row(modifier = Modifier.padding(5.dp)){
            VideoPlayer("FyjZLPmZ534")
        }
    }
}

@Composable
fun ScrollableColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.TopStart) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()), content = content)
    }
}

@Composable
public fun VideoPlayer(videoId: String) {
    YouTubePlayerComponent(videoId)
}

@Composable
fun YouTubePlayerComponent(videoId: String) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics

    val screenWidthPx = displayMetrics.widthPixels
    val playerWidthPx = (screenWidthPx * 1) // Adjust the percentage as needed
    val playerHeightPx = (playerWidthPx * 9 / 16) // Assuming 16:9 aspect ratio, you can adjust as needed

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    playerWidthPx,
                    playerHeightPx
                )
                settings.javaScriptEnabled = true
                loadUrl("https://www.youtube.com/embed/$videoId")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}

