package com.example.alcanzar.acerca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.ui.theme.AlcanzARTheme

class AcercaActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcanzARTheme {
                AcercaScreen {
                    finish()
                }
            }
        }
    }
}