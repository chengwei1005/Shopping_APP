package com.example.shopping_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shopping_app.R
import com.example.shopping_app.controller.CartController
import com.example.shopping_app.controller.ProductController


@Composable
fun ProductDetailScreen(
    productId: Long, navController: NavHostController, cartController: CartController
) {
    val productController: ProductController = viewModel()
//    val cartController: CartController = viewModel()
    LaunchedEffect(Unit) {
        cartController.getAllCartItems()
    }
    val cartItemCount = cartController.cartList.sumOf { it.quantity }
    Scaffold(
        topBar = {
            TopAppBar(
                cartItemCount = cartItemCount,
                onBackClick = { navController.popBackStack() },
                onShoppingCartClick = { navController.navigate("cart") }
            )
        },
        bottomBar = { BottomAppBar(productId, cartController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ProductPicture()
            Spacer(modifier = Modifier.size(10.dp))
            ProductDescription(productId, productController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    cartItemCount: Int,
    onBackClick: () -> Unit,
    onShoppingCartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "goBack",
                modifier = Modifier.size(28.dp)
            )
        }
        BadgedBox(
            modifier = Modifier.padding(end = 8.dp),
            badge = {
                if (cartItemCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        modifier = Modifier.offset(x = (-8).dp, y = 8.dp)
                    ) {
                        Text(text = cartItemCount.toString())
                    }
                }
            }
        ) {
            IconButton(onClick = onShoppingCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "shoppingCart",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun ProductPicture() {
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    Spacer(modifier = Modifier.size(10.dp))
    HorizontalPager(state = pagerState) {
        Image(
            painterResource(R.drawable.test_image),
            contentDescription = "product screen",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ProductDescription(productId: Long, productController: ProductController) {
    val product = productController.productList.find { it.id.toLong() == productId }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(product?.name ?: "")
        Spacer(modifier = Modifier.size(16.dp))
        Text(product?.description ?: "")
    }
}

@Composable
fun BottomAppBar(productId: Long, cartController: CartController) {
    val isLoading = cartController.isLoading
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedButton(
            onClick = {
                cartController.addToCart(productId)
            },
            modifier = Modifier.weight(1f),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Blue,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.Blue)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text("Add to Cart")
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {
                cartController.getAllCartItems()
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Buy Now")
        }
    }
}