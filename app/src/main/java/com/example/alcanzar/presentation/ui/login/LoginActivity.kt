package com.example.alcanzar.presentation.ui.login

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
import com.example.alcanzar.presentation.ui.registro.RegistroActivity
import com.example.alcanzar.presentation.viewmodel.LoginViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : ComponentActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val userDataSource = UserFirestoreDataSource(db)
        val loginRepository = LoginRepositoryImpl(userDataSource)

        val iniciarSesionUseCase = IniciarSesionUseCase(loginRepository)
        val crearCuentaUseCase = CrearCuentaUseCase(loginRepository)

        viewModel = LoginViewModel(
            iniciarSesionUseCase,
            crearCuentaUseCase
        )

        setContent {
            AlcanzARTheme {
                LoginScreen(
                    onLogin = { usuario, password ->
                        viewModel.iniciarSesion(usuario, password) { userId ->
                            if (userId != null) {
                                SessionManager.guardarUsuarioId(this, userId)
                                startActivity(Intent(this, BienvenidaActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onCrearCuentaClick = {
                        startActivity(Intent(this, RegistroActivity::class.java))
                    }
                )
            }
        }
    }
}
