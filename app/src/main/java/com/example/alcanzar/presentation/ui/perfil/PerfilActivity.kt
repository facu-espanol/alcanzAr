package com.example.alcanzar.presentation.ui.perfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcanzar.data.repository.UsuarioRepositoryImpl
import com.example.alcanzar.domain.usecase.GetUsuarioUseCase
import com.example.alcanzar.presentation.viewmodel.PerfilViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Preparamos las dependencias (Manual Dependency Injection)
        val firestore = FirebaseFirestore.getInstance()
        val repository = UsuarioRepositoryImpl(firestore)
        val getUsuarioUseCase = GetUsuarioUseCase(repository)

        setContent {
            AlcanzARTheme {
                // 2. Creamos el ViewModel usando una Factory para pasarle el UseCase
                val viewModel: PerfilViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return PerfilViewModel(getUsuarioUseCase) as T
                        }
                    }
                )

                // 3. Pasamos el viewModel a la pantalla
                PerfilScreen(viewModel = viewModel)
            }
        }
    }
}