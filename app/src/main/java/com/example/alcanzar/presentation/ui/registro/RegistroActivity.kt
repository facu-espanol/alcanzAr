package com.example.alcanzar.presentation.ui.registro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.CrearCuentaUseCase
import com.example.alcanzar.domain.usecase.IniciarSesionUseCase
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.viewmodel.LoginViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : ComponentActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = UserFirestoreDataSource(FirebaseFirestore.getInstance())
        val repository = LoginRepositoryImpl(dataSource)
        val iniciarSesionUseCase = IniciarSesionUseCase(repository)
        val crearCuentaUseCase = CrearCuentaUseCase(repository)

        viewModel = LoginViewModel(iniciarSesionUseCase, crearCuentaUseCase)

        setContent {
            AlcanzARTheme {
                RegistroScreen(
                    onCrearCuenta = { usuario, password, onResult ->
                        viewModel.crearCuenta(usuario, password) { userId ->
                            if (userId != null) {
                                SessionManager.guardarUsuarioId(this, userId)
                                onResult(true)
                            } else {
                                onResult(false)
                            }
                        }
                    },
                    onRegistroSuccess = {
                        startActivity(Intent(this, BienvenidaActivity::class.java))
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
