package com.example.shopping_app

import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Button
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopping_app.controller.CartController
import com.example.shopping_app.controller.LoginController
import com.example.shopping_app.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            androidx.compose.material3.MaterialTheme {
                val navController = rememberNavController()
                val cartController: CartController = viewModel()
                AppNavGraph(navController = navController, cartController = cartController)
            }
        }
    }
}

//@Composable
//fun LoginScreen(loginController: LoginController = viewModel()) {
//    var isLogin by remember { mutableStateOf(true) }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(60.dp))
//
//        // 頂部頁籤 (Log in / Sign up)
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .clickable {
//                        isLogin = true
//                        loginController.password = ""
//                        loginController.confirmPassword = ""
//                    },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    "Log in",
//                    fontSize = 18.sp,
//                    fontWeight = if (isLogin) FontWeight.Bold else FontWeight.Normal,
//                    color = if (isLogin) Color.Black else Color.Gray
//                )
//                if (isLogin) {
//                    Divider(
//                        color = Color(0xFF5D5FEF),
//                        thickness = 2.dp,
//                        modifier = Modifier
//                            .width(60.dp)
//                            .padding(top = 4.dp)
//                    )
//                }
//            }
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .clickable {
//                        isLogin = false
//                        loginController.password = ""
//                        loginController.confirmPassword = ""
//                    },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    "Sign up",
//                    fontSize = 18.sp,
//                    fontWeight = if (!isLogin) FontWeight.Bold else FontWeight.Normal,
//                    color = if (!isLogin) Color.Black else Color.Gray
//                )
//                if (!isLogin) {
//                    Divider(
//                        color = Color(0xFF5D5FEF),
//                        thickness = 2.dp,
//                        modifier = Modifier
//                            .width(60.dp)
//                            .padding(top = 4.dp)
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(40.dp))
//        if (isLogin) {
//            LoginForm(loginController)
//        } else {
//            RegisterForm(false, loginController)
//        }
//    }
//}
//
//
//@Composable
//fun SocialLoginButton(text: String, iconResId: Int) {
//    OutlinedButton(
//        onClick = { },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp),
//        shape = RoundedCornerShape(12.dp),
//        border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.5.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Icon(
//                painter = painterResource(id = iconResId),
//                contentDescription = null,
//                modifier = Modifier.size(24.dp),
//                tint = Color.Unspecified
//            )
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Text(text = text, color = Color.Black, fontSize = 16.sp)
//        }
//    }
//}
//
//@Composable
//fun LoginForm(loginController: LoginController) {
//    var passwordVisible by remember { mutableStateOf(false) }
//    Column {
//        // Email 輸入框
//        Text("Email", fontWeight = FontWeight.SemiBold)
//        OutlinedTextField(
//            value = loginController.email,
//            onValueChange = { loginController.email = it },
//            placeholder = { Text("Enter your email") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // 密碼輸入框
//        Text("Password", fontWeight = FontWeight.SemiBold)
//        OutlinedTextField(
//            value = loginController.password,
//            onValueChange = { loginController.password = it },
//            placeholder = { Text("Enter your password") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            trailingIcon = {
//                val image = if (passwordVisible) {
//                    painterResource(id = android.R.drawable.ic_menu_view)
//                } else {
//                    painterResource(id = android.R.drawable.ic_partial_secure)
//                }
//                IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                    Icon(
//                        painter = image,
//                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//        )
//        Text(
//            text = "Forget password",
//            color = Color.Blue,
//            fontSize = 12.sp,
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(top = 4.dp, end = 8.dp)
//        )
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        // 登入按鈕 (連動 API)
//        Button(
//            onClick = { },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D5FEF))
//        ) {
//            Text("Login", color = Color.White)
//        }
//        Spacer(modifier = Modifier.height(40.dp))
//
//        // 分隔線
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Divider(modifier = Modifier.weight(1f))
//            Text(" Or ", color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
//            Divider(modifier = Modifier.weight(1f))
//        }
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        // 第三方登入 (Apple/Google)
//        SocialLoginButton(text = "Login with Apple", iconResId = R.drawable.apple_logo)
//        Spacer(modifier = Modifier.height(12.dp))
//        SocialLoginButton(text = "Login with Google", iconResId = R.drawable.google_logo)
//    }
//}
//
//@Composable
//fun RegisterForm(isLogin: Boolean, loginController: LoginController) {
//    var passwordVisible by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text("Name", fontWeight = FontWeight.SemiBold)
//        OutlinedTextField(
//            value = loginController.name,
//            onValueChange = { loginController.name = it },
//            placeholder = { Text("Enter your Name") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text("Email", fontWeight = FontWeight.SemiBold)
//        OutlinedTextField(
//            value = loginController.email,
//            onValueChange = { loginController.email = it },
//            placeholder = { Text("Enter your Email") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text("Password", fontWeight = FontWeight.SemiBold)
//        OutlinedTextField(
//            value = loginController.password,
//            onValueChange = { loginController.password = it },
//            placeholder = { Text("Enter your password") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            isError = loginController.isPasswordMismatch,
//            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            trailingIcon = {
//                val image = if (passwordVisible) {
//                    painterResource(id = android.R.drawable.ic_menu_view)
//                } else {
//                    painterResource(id = android.R.drawable.ic_partial_secure)
//                }
//                IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                    Icon(
//                        painter = image,
//                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text("Confirm Password", fontWeight = FontWeight.SemiBold)
//        OutlinedTextField(
//            value = loginController.confirmPassword,
//            onValueChange = { loginController.confirmPassword = it },
//            placeholder = { Text("Enter your password again") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            isError = loginController.isPasswordMismatch,
//            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            trailingIcon = {
//                val image = if (passwordVisible) {
//                    painterResource(id = android.R.drawable.ic_menu_view)
//                } else {
//                    painterResource(id = android.R.drawable.ic_partial_secure)
//                }
//                IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                    Icon(
//                        painter = image,
//                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//        )
//        if (loginController.isPasswordMismatch) {
//            Text(
//                text = "密碼不一致",
//                color = Color.Red,
//                fontSize = 12.sp,
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .padding(top = 4.dp, end = 8.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        //動態改變按鈕文字與功能
//        Button(
//            onClick = {
//                if (isLogin) {
//                    loginController.email.isNotEmpty() && loginController.password.isNotEmpty()
//                    loginController.login(
//                        onSuccess = { /* 1.存token 2.進入主頁 */ },
//                        onFailure = { /* 顯示失敗訊息 */ })
//                } else {
//                    loginController.register(
//                        onSuccess = { /* 顯示註冊成功並回到註冊頁面 */ },
//                        onFailure = { /* 顯示失敗訊息並清空欄位資料 */ })
//                }
//            },
//            enabled = !loginController.isPasswordMismatch && loginController.name.isNotEmpty() && loginController.email.isNotEmpty() && loginController.password.isNotEmpty(),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D5FEF))
//        ) {
//            Text(if (isLogin) "Login" else "Create Account", color = Color.White)
//        }
//    }
//}