package com.example.barcodescanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.barcodescanapp.ui.screens.configuration.ConfigurationScreen
import com.example.barcodescanapp.ui.screens.MainScreen
import com.example.barcodescanapp.ui.theme.BarCodeScanAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarCodeScanAppTheme(darkTheme = true) {
                BarCodeScanApp()
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BarCodeScanAppPreview(){
    BarCodeScanAppTheme(darkTheme = true) {
        BarCodeScanApp()
    }
}


@Composable
fun BarCodeScanApp(
    navController: NavHostController = rememberNavController()
) {


    val backStackEntry = navController.currentBackStackEntryAsState()

    val currentScreen = BarCodeScanScreen.valueOf(
        backStackEntry.value?.destination?.route
            ?: BarCodeScanScreen.Start.name
    )

    NavHost(
        navController = navController,
        startDestination = BarCodeScanScreen.Start.name,

        ){
            composable(
                route = BarCodeScanScreen.Start.name,
            ){
                MainScreen(
                    nav= { navController.navigate(BarCodeScanScreen.Configuration.name)},
                    title = stringResource(currentScreen.title),
                )
            }
            composable(route = BarCodeScanScreen.Configuration.name){
                ConfigurationScreen()
            }
    }




}

enum class BarCodeScanScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    Configuration(title = R.string.configs)
}

