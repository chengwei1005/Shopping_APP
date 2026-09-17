package com.example.shopping_app.ui.screens

import com.example.shopping_app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shopping_app.controller.CartController
import com.example.shopping_app.controller.ProductController
import coil.compose.AsyncImage
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ProductScreen(
    navController: NavController,
    productController: ProductController = viewModel(),
    cartController: CartController = viewModel()
) {
    LaunchedEffect(Unit) {
        cartController.getAllCartItems()
    }
    val categories =
        listOf("Meats & Fishes", "Vegetables", "Fruits", "Home & Living")
    val products = productController.productList
    val cartItemCount = cartController.cartList.sumOf { it.quantity }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 第一行：搜尋列
        item(span = { GridItemSpan(2) }) {
            TopSearchBar(
                cartItemCount = cartItemCount,
                onSearchClick = {},
                onMenuClick = {navController.navigate("orders")},
                onCartClick = { navController.navigate("cart") })
        }

        // 第二行：Banner
        item(span = { GridItemSpan(2) }) {
            PromotionBanner()
        }

        // 第三行：分類
        item(span = { GridItemSpan(2) }) {
            CategorySection(categories)
        }

        //商品
        items(productController.productList, key = { product -> product.id }) { product ->
            ProductItem(
                id = product.id.toString(),
                name = product.name,
                price = product.price.toString(),
                imageRes = product.imageUrl,
                navController
            )
        }
    }
}


@Composable
fun TopSearchBar(cartItemCount: Int,onSearchClick: () -> Unit, onMenuClick: () -> Unit, onCartClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 選單按鈕
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onCartClick) {
            BadgedBox(
                badge = {
                    if (cartItemCount > 0) {
                        Badge {
                            Text(text = if (cartItemCount > 99) "99+" else cartItemCount.toString())
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "ShoppingCart",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        // 搜尋按鈕
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.size(28.dp),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun PromotionBanner() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box {
            // 背景圖
            Image(
                painter = painterResource(id = R.drawable.grocery_store),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

//            Column(modifier = Modifier.padding(16.dp)) {
//                Text("New Release", color = Color.Gray, fontSize = 14.sp)
//                Text("Fresh Fruits", fontWeight = FontWeight.Bold, fontSize = 24.sp)
//                Spacer(modifier = Modifier.height(8.dp))
//                Button(
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD35400))
//                ) {
//                    Text("Shop Now")
//                }
//            }
        }
    }
}

@Composable
fun CategorySection(categories: List<String>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(70.dp)
                        .border(1.dp, Color.LightGray, CircleShape),
                    color = Color(0xFFF5F5F5)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.test_image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                Text(category, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
fun ProductItem(
    id: String,
    name: String,
    price: String,
    imageRes: String?,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(20.dp)
                    .clickable { /* 收藏邏輯 */ }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("product_detail/${id}")
                    }
            ) {
                // 圖片
                AsyncImage(
                    model = imageRes,
                    contentDescription = name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Fit
                )

                // 商品資訊
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "NTD :  $price",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                }
            }

            //  購物車按鈕
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .clickable { /* 加入購物車 */ },
                color = Color.Transparent
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}