package com.example.alcanzar.presentation.ui.perfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.ui.theme.AlcanzARTheme

class PerfilActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcanzARTheme {
                PerfilScreen()
            }
        }
    }
}