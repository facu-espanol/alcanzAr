package com.example.alcanzar.presentation.ui.registro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.domain.usecase.CrearCuentaUseCase
import com.example.alcanzar.domain.usecase.IniciarSesionUseCase
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.viewmodel.LoginViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class RegistroActivity : ComponentActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = LoginRepositoryImpl(this)
        val iniciarSesionUseCase = IniciarSesionUseCase(repository)
        val crearCuentaUseCase = CrearCuentaUseCase(repository)

        viewModel = LoginViewModel(
            iniciarSesionUseCase = iniciarSesionUseCase,
            crearCuentaUseCase = crearCuentaUseCase
        )

        setContent {
            AlcanzARTheme {
                RegistroScreen(
                    onCrearCuenta = { usuario, password ->
                        viewModel.crearCuenta(usuario, password)
                    },
                    onRegistroSuccess = {
                        startActivity(
                            Intent(this, BienvenidaActivity::class.java)
                        )
                        finish()
                    },
                    onVolverLogin = {
                        finish()
                    }
                )
            }
        }
    }
}