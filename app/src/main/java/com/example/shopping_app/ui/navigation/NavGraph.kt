package com.example.shopping_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopping_app.ui.screens.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        composable("auth") {
            LoginScreen()
        }

        composable("home") {
            // 這裡放你之後要做的首頁
            // Text("Welcome to Home Screen!")
        }
    }
}