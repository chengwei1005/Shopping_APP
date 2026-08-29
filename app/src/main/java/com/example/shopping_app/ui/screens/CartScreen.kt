package com.example.shopping_app.ui.screens

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shopping_app.model.CartItemResponse
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopping_app.controller.CartController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartController: CartController = viewModel(), navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        cartController.getAllCartItems()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Shopping Cart") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        },
        bottomBar = {
            if (cartController.cartList.isNotEmpty()) {
                CartItemTotal(
                    subtotal = cartController.subtotalAmount,
                    deliveryFee = 30.0,
                    onCheckoutClick = {

                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (cartController.isLoading && cartController.cartList.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (cartController.cartList.isEmpty()) {
                // 購物車空空的提示
                Text(text = "Your cart is empty", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartController.cartList) { item ->
                        CartItemRow(item, cartController = cartController)
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItemResponse,
    modifier: Modifier = Modifier,
    cartController: CartController
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左側：商品圖片占位
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0F2F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_gallery),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 中間：名稱與單價
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.productName, //
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1D20)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "NTD ${item.subTotal}",
                    fontSize = 16.sp,
                    color = Color(0xFF6C757D)
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 減號按鈕
                IconButton(
                    onClick = {
                        cartController.updateItemQuantity(item.cartItemId,false)
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF8F9FA))
                ) {
                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                // 數量顯示
                Text(
                    text = "${item.quantity}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                // 加號按鈕
                IconButton(
                    onClick = {
                        cartController.updateItemQuantity(item.cartItemId,true)
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF8F9FA))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CartItemTotal(
    subtotal: Double,
    deliveryFee: Double = 2.00,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .navigationBarsPadding()
        ) {
            PriceRow(label = "Subtotal", price = subtotal)
            Spacer(modifier = Modifier.height(12.dp))
            PriceRow(label = "Delivery", price = deliveryFee)
            Spacer(modifier = Modifier.height(12.dp))
            PriceRow(label = "Total", price = subtotal + deliveryFee)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F55A4))
            ) {
                Text(
                    text = "Proceed To checkout",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun PriceRow(label: String, price: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 16.sp, color = Color(0xFF6C757D))
        Text(
            text = "NTD ${String.format("%.2f", price)}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF212529)
        )
    }
}
