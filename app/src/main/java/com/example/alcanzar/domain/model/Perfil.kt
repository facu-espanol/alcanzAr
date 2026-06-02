package com.example.alcanzar.domain.model
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcanzar.data.repository.UsuarioRepositoryImpl
import com.example.alcanzar.domain.usecase.GetUsuarioUseCase
import com.example.alcanzar.presentation.ui.perfil.PerfilScreen
import com.example.alcanzar.presentation.viewmodel.PerfilViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class Perfil : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Preparamos las dependencias fuera del setContent
        val firestore = FirebaseFirestore.getInstance()
        val repository = UsuarioRepositoryImpl(firestore)
        val getUsuarioUseCase = GetUsuarioUseCase(repository)

        setContent {
            AlcanzARTheme {
                // Usamos una Factory para instanciar el ViewModel correctamente en Compose
                val viewModel: PerfilViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return PerfilViewModel(getUsuarioUseCase) as T
                        }
                    }
                )

                PerfilScreen(viewModel)
            }
        }
    }
}