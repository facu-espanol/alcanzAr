package com.example.alcanzar.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.bienvenida.BienvenidaActivity
import com.example.alcanzar.ui.theme.AlcanzARTheme

class LoginActivity : ComponentActivity() {

    private val usuarioCorrecto = "admin"
    private val passwordCorrecta = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlcanzARTheme {
                LoginScreen(
                    usuarioCorrecto = usuarioCorrecto,
                    passwordCorrecta = passwordCorrecta,
                    onLoginSuccess = {
                        startActivity(
                            Intent(
                                this,
                                BienvenidaActivity::class.java
                            )
                        )
                        finish()
                    }
                )
            }
        }
    }
}