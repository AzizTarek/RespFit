package com.example.respfitprototype.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Grade
import androidx.compose.material.icons.rounded.NewReleases
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.navigation.*
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.theme.AppTheme
import com.example.respfitprototype.ui.theme.spacing
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    // Get the current UserDetails object from the ViewModel
    val userDetails = viewModel?.userDetails?.observeAsState()?.value

      Card(          //Dashboard
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
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


                //Header & Date Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "DASHBOARD",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.weight(1f) // This makes the Text take up more space (start position)
                    )

                    val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                    val current = LocalDateTime.now().format(formatter)
                    Text(
                        text = current,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f) // This makes the Text take up less space (end position)
                    )
                }


                //Weight Optimization Row
                Row(modifier = Modifier
                    .padding(20.dp)
                    .clickable {
                        navController.navigate(ROUTE_WEIGHT) {
                            popUpTo(ROUTE_WEIGHT) {
                                inclusive = false
                            } //a previously accessed screen will be deleted from memory , when user presses back they wont be redirected to login after logging in

                        }
                    }) { //Progress bar

                    Column() {
                        Text(  //Heading
                            modifier = Modifier.padding(4.dp),
                            text = "Weight Optimization",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )

                        Box(contentAlignment = Alignment.CenterStart)
                        {//Progress bar

                            Column() {
                                LinearProgressIndicator(progress = (userDetails?.weightProgress?:0f)/100f,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(CircleShape)
                                        .height(14.dp))
                            }
                            Text(text = ((userDetails?.weightProgress?:0).toInt()).toString()+" % Complete",
                                fontSize = 13.sp, color = Color.DarkGray,modifier = Modifier.padding(start = 35.dp))
                        }
                    }
                }

                //Physical Ability Row
                Row(modifier = Modifier
                    .padding(20.dp)
                    .clickable {
                        navController.navigate(ROUTE_FITNESS) {
                            popUpTo(ROUTE_FITNESS) {
                                inclusive = false
                            } //a previously accessed screen will be deleted from memory , when user presses back they wont be redirected to login after logging in

                        }
                    }) { //Progress bar

                    Column() {
                        Text(  //Heading
                            modifier = Modifier.padding(4.dp),
                            text = "Physical Ability",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                        Box(contentAlignment = Alignment.CenterStart)
                        {//Progress bar

                            Column() {
                                LinearProgressIndicator(progress = (viewModel?.userDetails?.value?.physicalActivityProgress?:0f)/100f,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(CircleShape)
                                        .height(14.dp))
                            }
                            Text(text = ((viewModel?.userDetails?.value?.physicalActivityProgress?:0).toInt()).toString()+" % Complete",fontSize = 13.sp, color = Color.DarkGray,modifier = Modifier.padding(start = 35.dp))
                        }
                    }
                }

                //Badges Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(  //Heading
                        modifier = Modifier.padding(4.dp),
                        text = "Badges",
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp)) {



                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                      Icon(imageVector = Icons.Rounded.Grade, contentDescription ="grade icon" )
                      Text(
                          modifier = Modifier.padding(4.dp),
                          text = "Newbie",
                          fontSize = 14.sp,
                          color = MaterialTheme.colors.onSurface
                      )
                  }

                }
            }

        } //Caard end
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}
