package com.example.alcanzar.presentation.ui.perfil

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcanzar.data.repository.UsuarioRepositoryImpl
import com.example.alcanzar.domain.usecase.GetUsuarioUseCase
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.viewmodel.PerfilViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()
        val repository = UsuarioRepositoryImpl(firestore)
        val getUsuarioUseCase = GetUsuarioUseCase(repository)

        val targetUserId = intent.getStringExtra("USER_ID")

        setContent {
            AlcanzARTheme {
                val viewModel: PerfilViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return PerfilViewModel(getUsuarioUseCase) as T
                        }
                    }
                )

                PerfilScreen(
                    viewModel = viewModel,
                    targetUserId = targetUserId,
                    onBack = { finish() },
                    onLogout = {
                        val intent = Intent(this, BienvenidaActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
