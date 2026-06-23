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
import com.slangmap.app.presentation.home.model.HomeUiState
import com.slangmap.app.presentation.home.model.StoreUiModel
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
                            isLoginInProgress = false,
                            onLoginClick = {
                                navController.navigate("home")
                            },
                            onSearchNearbyClick = {},
                            onReviewClick = {},
                            onReportClick = {},
                            onTermsClick = {},
                            onPrivacyPolicyClick = {}
                        )
                    }

                    composable("home") {

                        HomeScreen(
                            uiState = HomeUiState(
                                stores = listOf(
                                    StoreUiModel(
                                        id = 1,
                                        name = "슬라임 팩토리",
                                        category = "슬라임",
                                        distance = "320m",
                                        rating = 4.8
                                    ),
                                    StoreUiModel(
                                        id = 2,
                                        name = "문구좋아 목동점",
                                        category = "말랑이",
                                        distance = "890m",
                                        rating = 4.6
                                    )
                                )
                            ),
                            onStoreClick = { storeId ->
                                // TODO: 매장 상세 화면 이동
                            }
                        )
                    }
                }
            }
        }
    }
}