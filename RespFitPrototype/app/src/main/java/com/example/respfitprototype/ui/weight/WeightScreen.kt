package com.example.respfitprototype.ui.weight
import android.webkit.WebView
import androidx.compose.ui.unit.dp

import android.content.res.Configuration
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.R
import com.example.respfitprototype.ui.UserDetails
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.exercises.VideoPlayer
import com.example.respfitprototype.ui.theme.AppTheme
import com.example.respfitprototype.ui.theme.spacing
import com.example.respfitprototype.ui.home.HomeScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeightScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    // Get the current UserDetails object from the ViewModel
    val userDetails = viewModel?.userDetails?.observeAsState()?.value

    val weight = (userDetails?.weight?:1f).toFloat()
    val height = (userDetails?.height?:1f).toFloat()
    val BMI = (weight  / ((height  / 100f) * (height / 100f))).toInt()
    Column {
        Card(          //Weight panel
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
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )  //Align all items in the center
            {

                Row(//goal name + Description
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = userDetails?.weightLevel?.getGoalName() ?:"",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.weight(1f) // This makes the Text take up more space (start position)
                    )
//                    Text(
//                        text = "Description",
//                        style = MaterialTheme.typography.subtitle2,
//                        color = MaterialTheme.colors.onSurface,
//                        textAlign = TextAlign.End,
//                        modifier = Modifier.weight(1f) // This makes the Text take up less space (end position)
//                    )
                }

                Row( //Weight info
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(   modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally ) {
                        Icon(Icons.Outlined.MonitorWeight, "monitor weight icon", tint = MaterialTheme.colors.onSurface)
                        Text(
                            text = userDetails?.weight?.toString()?:""+" kg",
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 15.sp,
                        )
                        Text(
                            text = "Starting weight",
                            fontSize = 10.sp,
                            color = Color.Gray,
                        )
                    }
                    Column( modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally ) {
                        Icon(Icons.Outlined.MonitorWeight, "monitor weight icon", tint = MaterialTheme.colors.onSurface)
                        Text(
                            text = userDetails?.weight?.toString()?:""+" kg",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.onSurface,
                        )
                        Text(
                            text = "Current weight",
                            fontSize = 10.sp,
                            color = Color.Gray,
                        )
                    }

                    Column(   modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Adjust, "adjust icon", tint = MaterialTheme.colors.onSurface)
                        Text(
                            text =  if(BMI>=25)
                                (weight-2f).toString()
                                    else if(BMI<=18)
                                (weight+2f).toString()
                                    else
                                (weight).toString()
                            ,
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.onSurface,
                        )
                        Text(
                            text = "Target weight",
                            fontSize = 10.sp,
                            color = Color.Gray,
                        )
                    }
                } // End of weight info
                Row( // BMI info
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box( modifier = Modifier
                        .width(300.dp)
                        .height(150.dp),
                        contentAlignment = Alignment.Center){
                        Image(
                            painter = painterResource(id = R.drawable.bmi),
                            contentDescription = "bmi scale",
                            modifier = Modifier
                                .matchParentSize()
                        )
                        Text(
                            text = "Your BMI is " + BMI.toString(),
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                } //end of bmi info
            } //Card end


        }

        Card(          //Calories panel
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium)
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            backgroundColor = MaterialTheme.colors.primaryVariant,
            elevation = 4.dp,
            shape = RoundedCornerShape(14.dp)
        )
        {
            Row( //Calorie info
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(   modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally ) {
                    Icon(Icons.Outlined.RunCircle, "run circle icon", tint = MaterialTheme.colors.onSurface)
                    Text(
                        text = "2000 kcal",
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 15.sp,
                    )
                var age by remember { mutableStateOf("2000") }

//                    BasicTextField(
//                        value = age,
//                        onValueChange = {
//                            age = it
//                        },
//                        textStyle = TextStyle(
//                            color = Color.Black, // Change the text color to Black
//                            fontSize = 18.sp // Change the text size to 18sp
//                        ),
//                        keyboardOptions = KeyboardOptions(
//                            capitalization = KeyboardCapitalization.None,
//                            autoCorrect = false,
//                            keyboardType = KeyboardType.Number,
//                            imeAction = ImeAction.Next
//                        ),
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth(0.7f)
//                            .padding(top = 10.dp)
//                            .padding(5.dp)
//                            .border(0.dp, Color.Transparent) // Make the border outline invisible
//                    )
//





                Text(
                        text = "Calories burnt",
                        fontSize = 10.sp,
                        color = Color.Gray,
                    )
                }
                Column( modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally ) {
                    Icon(Icons.Outlined.LunchDining, "lunch dining icon", tint = MaterialTheme.colors.onSurface)
                    Text(
                        text = "0 kcal",
                        fontSize = 15.sp,
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = "Calories consumed",
                        fontSize = 10.sp,
                        color = Color.Gray,
                    )
                }
            } // End of calorie info

            Row ( // Progress bar
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(top = 55.dp),      verticalAlignment = Alignment.CenterVertically
            )
            {

                Box( )
                {//Progress bar
                        LinearProgressIndicator(progress = 0.0f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .height(18.dp))
                    Text(text = "0/4000" + " Total calories lost", fontSize = 13.sp, color = Color.DarkGray,modifier = Modifier.padding(start = 25.dp) )
                }


            }
        }
       VideoPlayer("_KGEkwSRoF0")
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

