# 🛒 E-Commerce Full-Stack App

這是一個完整的全端電商應用程式 (MVP)，前後端完全分離。
前端採用現代化的 Android UI 框架 **Jetpack Compose**，後端則基於 **Spring Boot** 提供 RESTful API 服務，並結合 Spring Security 與 JWT 實作安全的會員認證機制。

## 📱 畫面預覽 (Screenshots)
| 首頁 (商品列表) | 購物車 (Cart) | 歷史訂單 (Orders) |
| :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/98ee901a-635d-4716-97be-49b8481cffd1" width="250"/> | <img src="https://github.com/user-attachments/assets/ec5e6fb1-db85-4be2-8e2c-ba85aac32774" width="250"/> | <img src="https://github.com/user-attachments/assets/4c1a666d-8cd5-46a7-98ee-1e379e10d5a1" width="250"/> |

## ✨ 核心功能 (Features)

* **🔐 登入與安全認證**：使用 Spring Security 與 JWT (JSON Web Token) 進行身分驗證，保護使用者資料與訂單 API。
* **🛍️ 商品與購物車管理**：
  * 商品資料庫使用 UUID 作為唯一識別碼。
  * 支援跨頁面的購物車狀態管理，可動態新增商品與調整數量。
* **💳 結帳與訂單系統**：
  * 一鍵結帳功能，結帳後自動清空購物車。
  * 完整的關聯式資料庫設計 (User -> Orders -> OrderItems -> Product)。
  * 專屬歷史訂單頁面，可隨時查看過去的消費紀錄與明細。

## 🛠️ 技術棧 (Tech Stack)

### 前端 (Android / Mobile)
* **語言**：Kotlin
* **UI 框架**：Jetpack Compose (Material Design 3)
* **網路請求**：Retrofit, OkHttp, Gson/Moshi
* **架構與導航**：ViewModel, Coroutines, Jetpack Navigation

### 後端 (Backend)
* **語言與框架**：Java, Spring Boot 3
* **資料庫與 ORM**：MySQL, Spring Data JPA, Hibernate
* **安全機制**：Spring Security, JWT

## 🚀 快速啟動 (Getting Started)

### 1. 後端設定 (Spring Boot)
1. 確保已安裝 MySQL，並建立對應的資料庫（如 `ecommerce_db`）。
2. 在 `src/main/resources/application.properties` 中設定你的資料庫帳號密碼：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=你的帳號
   spring.datasource.password=你的密碼
