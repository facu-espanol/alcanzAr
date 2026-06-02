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

    fun cargarMiPerfil(context: android.content.Context) {

        state = state.copy(loading = true)

        val id = SessionManager.obtenerUsuarioId(context)

        if (id == null) {
            state = state.copy(loading = false, usuario = null)
            return
        }

        getUsuario(id) { usuario: Usuario? ->
            state = state.copy(
                usuario = usuario,
                loading = false
            )
        }
    }
}
