package com.example.respfitprototype.ui.progress

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.material.icons.rounded.TrackChanges
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.ui.UserDetails
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.theme.AppTheme
import com.example.respfitprototype.ui.theme.spacing
import com.example.respfitprototype.ui.home.HomeScreen
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ScrollableContent(viewModel: AuthViewModel?, navController: NavHostController) {
    // Get the current UserDetails object from the ViewModel
    val userDetails = viewModel?.userDetails?.observeAsState()?.value

    val physicalActivityLevel = userDetails?.physicalActivityLevel ?: UserDetails.PhysicalActivityLevel.LOW
    val exerciseExamples = physicalActivityLevel.getExerciseExamples()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 48.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Goals",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            // Display exercise examples based on the physical activity level
            exerciseExamples.forEachIndexed { index, example ->
                GoalItem(viewModel,
                    goalName = example,
                    goalDuration = "",
                    progress = 0f
                )
            }
        }


    }
}

@Composable
fun ProgressScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primaryVariant,
            elevation = 4.dp,
            shape = RoundedCornerShape(14.dp)
        ) {
            // Pass the viewModel to ScrollableContent
            ScrollableContent(viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun GoalItem(viewModel: AuthViewModel?,goalName: String, goalDuration: String, progress: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {


    }
    Row(
        modifier = Modifier
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
    {
        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 2.dp)
//            ) {
//                Column() {
//                    LinearProgressIndicator(
//                        progress = progress,
//                        modifier = Modifier
//                            .fillMaxWidth(0.8f)
//                            .clip(CircleShape)
//                            .height(10.dp)
//                    )
//                }
//                Text(
//                    text = (progress * 100).toInt().toString() + "% Complete",
//                    fontSize = 10.sp,
//                    color = MaterialTheme.colors.onSurface,
//                    modifier = Modifier.padding(start = 35.dp)
//                )
//            }

            Icon(
                imageVector = Icons.Rounded.TrackChanges,
                contentDescription = "Track changes icon",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = goalName,
                color = MaterialTheme.colors.onSurface,
                fontSize = 15.sp,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = goalDuration,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(5.dp)
            )
        }
        val physicalActivityLevel = viewModel?.userDetails?.value?.physicalActivityLevel ?: UserDetails.PhysicalActivityLevel.LOW
        val examplesCount = physicalActivityLevel.getExerciseExamples().size
        val newValue =( viewModel?.userDetails?.value?.physicalActivityProgress?:0f )+ ((1f/examplesCount.toFloat())*100f)
        Column(modifier = Modifier.fillMaxWidth(0.3f)) {
            IconButton(onClick = {
                viewModel?.databaseRef?.child("userDetails")?.child("physicalActivityProgress")?.setValue(newValue)
            }, modifier = Modifier.padding(5.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = "done icon"
                )
            }
        }

    }



}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}
