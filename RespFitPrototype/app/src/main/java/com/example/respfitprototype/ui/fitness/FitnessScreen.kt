package com.example.respfitprototype.ui.fitness

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.navigation.ROUTE_EXERCISES
import com.example.respfitprototype.navigation.ROUTE_PROGRESS
import com.example.respfitprototype.navigation.ROUTE_WEIGHT
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.theme.AppTheme
import com.example.respfitprototype.ui.theme.spacing
import com.example.respfitprototype.ui.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FitnessScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    // Get the current UserDetails object from the ViewModel
    val userDetails = viewModel?.userDetails?.observeAsState()?.value

    Column(  modifier = Modifier.fillMaxSize() ) {
        Row() {
            Card(         //Fitness programme
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 4.dp,
                shape = RoundedCornerShape(14.dp)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                )  //Align all items in the center
                {
                    Text(
                        text = "PROGRAMME",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = userDetails?.physicalActivityLevel?.getGoalName()?: "N/A",
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 17.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = userDetails?.physicalActivityLevel?.getGoalDescription()?: "N/A",
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            } //Card end
        }
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
           Button(onClick =
           {
               navController.navigate(ROUTE_PROGRESS){
               popUpTo(ROUTE_PROGRESS) {inclusive = true
               } //a previously accessed screen will be deleted from memory , when user presses back they wont be redirected to login after logging in
           } }, modifier = Modifier
               .padding(20.dp)
               .height(40.dp)) {
                Text(text = "View goals")
               Icon(imageVector = Icons.Rounded.NavigateNext, contentDescription = "Navigate next icon", modifier = Modifier.padding(start = 5.dp, end = 5.dp))
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
           Button(onClick = {
               navController.navigate(ROUTE_EXERCISES){
                   popUpTo(ROUTE_EXERCISES) {inclusive = true
                   } //a previously accessed screen will be deleted from memory , when user presses back they wont be redirected to login after logging in
               }
           }, modifier = Modifier
               .padding(20.dp)
               .height(40.dp)) {
                Text(text = "View exercises")
               Icon(imageVector = Icons.Rounded.NavigateNext, contentDescription = "Navigate next icon", modifier = Modifier.padding(start = 5.dp, end = 5.dp))
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
