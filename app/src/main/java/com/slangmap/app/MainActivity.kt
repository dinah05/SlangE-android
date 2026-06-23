package com.slangmap.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slangmap.app.presentation.home.HomeScreen
import com.slangmap.app.presentation.login.LoginScreen
import com.slangmap.app.presentation.splash.SplashScreen
import com.slangmap.app.ui.theme.SlangMapTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            SlangMapTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {

                    composable("splash") {
                        SplashScreen(
                            onStartClick = {
                                navController.navigate("login")
                            }
                        )
                    }

                    composable("login") {
                        LoginScreen(
                            onLoginClick = {
                                navController.navigate("home")
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}