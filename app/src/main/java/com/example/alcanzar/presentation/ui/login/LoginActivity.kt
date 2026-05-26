package com.example.alcanzar.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcanzar.data.repository.AuthRepositoryImpl
import com.example.alcanzar.domain.usecase.LoginUseCase
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.viewmodel.LoginViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepositoryImpl()
        val loginUseCase = LoginUseCase(repository)
        val loginViewModel = LoginViewModel(loginUseCase)

        setContent {
            AlcanzARTheme {
                LoginScreen(
                    onLogin = { usuario, password ->
                        loginViewModel.login(usuario, password)
                    },
                    onLoginSuccess = {
                        startActivity(
                            Intent(this, BienvenidaActivity::class.java)
                        )
                        finish()
                    }
                )
            }
        }
    }
}