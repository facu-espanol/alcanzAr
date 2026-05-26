package com.example.alcanzar.domain.model

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.presentation.ui.perfil.PerfilScreen
import com.example.alcanzar.ui.theme.AlcanzARTheme

class Perfil : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcanzARTheme {
                PerfilScreen()
            }
        }
    }
}