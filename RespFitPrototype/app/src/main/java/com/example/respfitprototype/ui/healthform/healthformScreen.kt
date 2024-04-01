package com.example.respfitprototype.ui.healthform

import android.content.res.Configuration
import android.os.Build
import android.service.autofill.UserData
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.theme.AppTheme
import com.example.respfitprototype.ui.theme.spacing
import com.example.respfitprototype.navigation.ROUTE_HOME
import com.example.respfitprototype.navigation.ROUTE_LOGIN
import com.example.respfitprototype.ui.UserDetails
import com.example.respfitprototype.ui.home.HomeScreen
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HealthFormScreen(viewModel: AuthViewModel?, navController: NavHostController ,snackbarHostState: SnackbarHostState) {
    // Get a reference to the custom database URL

    var openDialog by remember { mutableStateOf(false)} // Initially dialog is closed

    // resusable variables for dialogs
    var header by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    //Field variables
    var modExerciseMins by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember {mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var respiratoryDisType by remember { mutableStateOf("") }
    var vigExerciseMins by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, ) { //List of text fields

       Row(modifier = Modifier.fillMaxWidth().padding(end = 10.dp), horizontalArrangement = Arrangement.End) {
           IconButton(     //Logout button
               onClick = {
                   viewModel?.logout()
                   navController.navigate(ROUTE_LOGIN)
                   {
                       popUpTo(ROUTE_HOME) { inclusive = true}
                   }
               }

           ) {
               Row() {
                   Icon(
                       imageVector = Icons.Default.Logout,
                       contentDescription = "Logout Icon",
                       tint = MaterialTheme.colors.primary
                   )
                   Text(text = "Log out", color = MaterialTheme.colors.primary)

               }

           }
       }

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
            },
            label = { Text(text = "Age")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 10.dp)
                .padding(5.dp)
            ,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = height,
            onValueChange = {
                height = it
            },
            label = { Text(text = "Height (cm)")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(5.dp)
            ,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = weight,
            onValueChange = {
                weight = it
            },
            label = { Text(text = "Weight (kg)")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(5.dp)
            ,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = modExerciseMins,
            onValueChange = {
                modExerciseMins = it
            },
            label = { Text(text = "Moderate Exercise Minutes")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(5.dp)
            ,
             keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true ,
            trailingIcon = {
                IconButton(
                    onClick = {header = "Moderate Exercise Minutes";description = "On a typical week, how much time do you spend in total on moderate physical activities where your heartbeat increases and you breathe faster (e.g. brisk walking, cycling as a means of transport or as exercise, heavy gardening, running or recreational sports).";openDialog = true},
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info Icon",
                        tint = Color.Gray
                    )
                }
            }
        )
        OutlinedTextField(
            value = vigExerciseMins,
            onValueChange = {
               vigExerciseMins = it
            },
            label = { Text(text = "Vigorous Exercise Minutes")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(5.dp)
            ,
             keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true ,
            trailingIcon = {
                IconButton(
                    onClick = {header = "Vigorous Exercise Minutes";description = "How much of the time that you spend on physical activities in a typical week, which you indicated above, do you spend in total on vigorous physical activities? This includes activities that get your heart racing, make you sweat and leave you so short of breath that speaking becomes difficult (e.g. swimming, running, cycling at high speeds, cardio training, weight-lifting or team sports such as football).";openDialog = true},
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info Icon",
                        tint = Color.Gray
                    )
                }
            }
        )
        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = respiratoryDisType,
            onValueChange = {
                respiratoryDisType = it
            },
            label = { Text(text = "Respiratory Disease Type")},
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(5.dp)
            ,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true ,
            trailingIcon = {
                IconButton(
                    onClick = {header = "Respiratory Disease Type";description = "Enter the name of the respiratory disease that you have.";openDialog = true},
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info Icon",
                        tint = Color.Gray
                    )
                }
            }, keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
        )

         Button(onClick =
         {// Submit button: Save all user data to the Realtime Database
           viewModel?.submitUserDetails(modExerciseMins.toInt(),vigExerciseMins.toInt(),age.toInt(),height.toFloat(),weight.toFloat(),respiratoryDisType)
              viewModel?.viewModelScope?.launch {
                     snackbarHostState.showSnackbar("Success!", "Saved Changes", SnackbarDuration.Short) }
         },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.5f) ) {
            Text(text = "Save")
        }

        if (openDialog) { //Triggers dialog
            DialogBox2FA({openDialog = false},header, description)
        }

    }

}




data class UserData(
    val userId: String,
    val fullName: String,
    val email: String,
//    val modExerciseMins: Int,
//    val vigExerciseMins: Int,
//    val age : Int,
//    val height: Float,
//    val weight: Float,
//    val respiratoryDisType : String,
    val userDetails: UserDetails,
    // Add more fields as needed
)


@Composable
private fun DialogBox2FA(onDismiss: () -> Unit, header: String, description: String) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = Color(0xFF35898f)),
                    contentAlignment = Alignment.Center
                ) {

                }

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    text = header,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    text = description,
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF35898f)),
                    onClick = {
                        onDismiss()
                    }) {
                    Text(
                        text = "Okay",
                        color = Color.White
                    )
                }
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
