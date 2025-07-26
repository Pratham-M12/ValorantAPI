package com.example.valorantapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.valorantapi.ui.LoginScreen
import com.example.valorantapi.ui.MainNavigation
import com.example.valorantapi.ui.theme.ValorantAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValorantAPITheme{
                var loggedIn by rememberSaveable { mutableStateOf(false) }
                if (!loggedIn) {
                    LoginScreen(onLoginSuccess = { loggedIn = true })
                } else {
                    MainNavigation()
                }
            }
        }
    }
}
