package com.example.respfitprototype.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.respfitprototype.data.Resource
import com.example.respfitprototype.data.Resource.Success
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.home.HomeScreen
import com.example.respfitprototype.ui.auth.LoginScreen
import com.example.respfitprototype.ui.auth.SignupScreen
import com.example.respfitprototype.ui.exercises.ExercisesScreen
import com.example.respfitprototype.ui.fitness.FitnessScreen
import com.example.respfitprototype.ui.healthform.HealthFormScreen
import com.example.respfitprototype.ui.progress.ProgressScreen
import com.example.respfitprototype.ui.weight.WeightScreen
import com.google.firebase.auth.FirebaseUser
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()

) {
    // Observe the loginFlow to determine the startDestination
    val loginFlow by viewModel.loginFlow.collectAsState()
    val signupFlow by viewModel.signupFlow.collectAsState()

    // Set the startDestination based on the login status
    val startDestination = when (val loginFlowValue = viewModel.loginFlow.value) {
        is Resource.Success -> {
            if (loginFlowValue.result != null) {
                // User is logged in, navigate to home
                ROUTE_HOME
            } else {
                // User is not logged in, navigate to login
                ROUTE_LOGIN
            }
        }
        else -> ROUTE_LOGIN // User is not logged in, navigate to login
    }

    // Create a MutableState for the drawer state
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() } // Create a SnackbarHostState
    val currentLoginFlow by rememberUpdatedState(loginFlow)
    val currentSignupFlow by rememberUpdatedState(signupFlow)
    val loginResource = viewModel.loginFlow.value
    val signupResource = viewModel.signupFlow.value

    Scaffold(
        scaffoldState = ScaffoldState(drawerState = drawerState,snackbarHostState = snackbarHostState),
        bottomBar = {
            // Use rememberUpdatedState to ensure recomposition of the BottomBar when loginFlow or signupFlow changes
            if (loginResource is Resource.Success && loginResource.result != null
                || signupResource is Resource.Success && signupResource.result != null
            ) {
                BottomBar(navController = navController)
            }
        },
        topBar = {
            if (loginResource is Resource.Success && loginResource.result != null
                || signupResource is Resource.Success && signupResource.result != null
            ) {
                CustomTopAppBar(navController = navController)
            }
        }
    )
    {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable(ROUTE_LOGIN) {
                LoginScreen(viewModel,navController,snackbarHostState)
            }
            composable(ROUTE_SIGNUP) {
                SignupScreen(viewModel,navController,snackbarHostState)
            }
            composable(ROUTE_HOME) {
                HomeScreen(viewModel,navController)
            }
            composable(ROUTE_FITNESS) {
                FitnessScreen(viewModel,navController)
            }
            composable(ROUTE_WEIGHT) {
                WeightScreen(viewModel,navController)
            }
            composable(ROUTE_HFORM) {
                HealthFormScreen(viewModel,navController,snackbarHostState)
            }
            composable(ROUTE_PROGRESS) {
                ProgressScreen(viewModel,navController)
            }
            composable(ROUTE_EXERCISES) {
                ExercisesScreen(viewModel,navController)
            }

        }
    }

}


@Composable
fun BottomBar (navController: NavHostController)
{
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Fitness,
        BottomBarScreen.Weight
    )
    val navBackStackEntry by  navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = MaterialTheme.colors.primary) {
        screens.forEach{
                screen-> AddItem(
            screen = screen,
            currentDestination =currentDestination ,
            navController = navController )
        }
    }
}
@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem( //Define some properties for a bottom navigation item
        label = {
            Text(text=screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any{
            it.route ==screen.route
        }  == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id) //Immediately go to start destination when back button is pressed
                launchSingleTop= true
            }
        }
    )

}


/**
 * App bar to display title and conditionally display the back navigation.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomTopAppBar(
    navController: NavHostController
) {


        TopAppBar(
            title = { Text( text = "RespFit")},
            actions = {
                val context = LocalContext.current
                IconButton(onClick = {
                        navController.navigate(ROUTE_HFORM){
                            popUpTo(ROUTE_HFORM) {inclusive = true} //a previously accessed screen will be deleted from memory , when user presses back they wont be redirected to login after logging in
                        }
                }) { Icon(Icons.Filled.Settings, "Settings button icon") }
            },
            // below line is use to give background color
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 12.dp
        )

}