package com.example.shopping_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.shopping_app.R
import com.example.shopping_app.controller.ProductController
import com.example.shopping_app.model.ProductResponse


@Composable
fun ProductDetailScreen(
    productId: Int, navController: NavHostController
) {
    val productController: ProductController = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(onBackClick = {
                navController.popBackStack();
            }, onShoppingCartClick = {})
        },
        bottomBar = { BottomAppBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ProductPicture()
            Spacer(modifier = Modifier.size(10.dp))
            ProductDescription(productId, productController)
        }
    }
}

@Composable
fun TopAppBar(onBackClick: () -> Unit, onShoppingCartClick: () -> Unit) {
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

        IconButton(onClick = onShoppingCartClick) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "shoppingCart",
                modifier = Modifier.size(28.dp)
            )
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
fun ProductDescription(productId: Int, productController: ProductController) {
    val product = productController.productList.find { it.id.toInt() == productId }
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
fun BottomAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedButton(
            onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(
                contentColor = Color.Blue,
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.Blue)
        ) {
            Text("Add to Cart")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {},
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Buy Now")
        }
    }
}