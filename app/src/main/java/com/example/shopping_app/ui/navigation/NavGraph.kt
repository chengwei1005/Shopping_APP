package com.example.shopping_app.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shopping_app.ui.screens.LoginScreen
import com.example.shopping_app.ui.screens.ProductDetailScreen
import com.example.shopping_app.ui.screens.ProductScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        composable("auth") {
            LoginScreen(navController = navController)
        }

        composable("home") {
            // homePage
            ProductScreen(navController = navController)
        }

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("productId") ?: 0L
            ProductDetailScreen(productId = id, navController = navController)
        }
    }
}