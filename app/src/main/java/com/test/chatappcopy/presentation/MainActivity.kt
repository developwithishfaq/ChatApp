package com.test.chatappcopy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.chatappcopy.presentation.auth.AuthenticationScreen
import com.test.chatappcopy.presentation.chat.ChatScreen
import com.test.chatappcopy.presentation.home.HomeScreen
import com.test.chatappcopy.presentation.navigation.Routes
import com.test.chatappcopy.ui.theme.ChatAppCopyTheme

val LocalNavController = compositionLocalOf<NavHostController> {
    error("")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ChatAppCopyTheme {
                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.AuthScreen.name
                    ) {
                        composable(Routes.AuthScreen.name) {
                            AuthenticationScreen()
                        }
                        composable(Routes.HomeScreen.name) {
                            HomeScreen()
                        }
                        composable(Routes.ChatScreen.name+"/{chatId}") {
                            ChatScreen()
                        }
                    }
                }
            }
        }
    }
}
