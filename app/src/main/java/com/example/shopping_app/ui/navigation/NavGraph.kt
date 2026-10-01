package com.example.shopping_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shopping_app.controller.CartController
import com.example.shopping_app.controller.OrderController
import com.example.shopping_app.ui.screens.CartScreen
import com.example.shopping_app.ui.screens.LoginScreen
import com.example.shopping_app.ui.screens.OrderScreen
import com.example.shopping_app.ui.screens.ProductDetailScreen
import com.example.shopping_app.ui.screens.ProductScreen

@Composable
fun AppNavGraph(navController: NavHostController, cartController: CartController,orderController: OrderController) {
    NavHost(
        navController = navController, startDestination = "auth"
    ) {
        composable("auth") {
            LoginScreen(navController = navController)
        }

        composable("home") {
            // homePage
            ProductScreen(navController = navController, cartController = cartController)
        }

        composable("orders") {
            OrderScreen(orderController = orderController,navController = navController)
        }

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                navController = navController,
                cartController = cartController
            )
        }

        composable("cart") {
            CartScreen(cartController = cartController, navController = navController)
        }
    }
}