package com.example.alcanzar.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.GetUsuarioUseCase
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.presentation.statedata.PerfilUiState

class PerfilViewModel(
    private val getUsuario: GetUsuarioUseCase
) : ViewModel() {

    var state by mutableStateOf(PerfilUiState())
        private set

    fun cargarPerfil(context: android.content.Context, userId: String? = null) {
        state = state.copy(loading = true)
        val id = userId ?: SessionManager.obtenerUsuarioId(context)

        if (id == null) {
            state = state.copy(loading = false, usuario = null)
            return
        }

        getUsuario(id) { usuario ->
            // Enriquecemos con datos mock si el usuario no existe en Firebase (para pruebas)
            val finalUser = usuario ?: when {
                id == "admin_user" || id == "driver_test" -> Usuario(
                    id = id,
                    nombreCompleto = if (id == "admin_user") "Facundo (Admin)" else "Juan Perez (Test)",
                    promedioConductor = 4.8,
                    totalConductor = 22,
                    promedioPasajero = 5.0,
                    totalPasajero = 14
                )
                id.contains("mock") || id.contains("test") -> Usuario(
                    id = id,
                    nombreCompleto = "Usuario de Staging",
                    promedioConductor = 4.5,
                    totalConductor = 8
                )
                else -> null
            }

            state = state.copy(usuario = finalUser, loading = false)
        }
    }
}
