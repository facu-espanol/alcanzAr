package com.example.alcanzar.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.usecase.ObtenerUsuariosPorIdsUseCase
import com.example.alcanzar.domain.usecase.ParticiparEnViajeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetalleViajeViewModel(
    private val participarEnViajeUseCase: ParticiparEnViajeUseCase,
    private val obtenerUsuariosPorIdsUseCase: ObtenerUsuariosPorIdsUseCase
) : ViewModel() {

    private val _viajeState = MutableStateFlow<Viaje?>(null)
    val viajeState = _viajeState.asStateFlow()

    private val _usuariosInscriptos = MutableStateFlow<List<Usuario>>(emptyList())
    val usuariosInscriptos = _usuariosInscriptos.asStateFlow()

    fun setViaje(viaje: Viaje) {
        _viajeState.value = viaje
        cargarUsuariosInscriptos(viaje.idPasajeros)
    }

    private fun cargarUsuariosInscriptos(ids: List<String>) {
        obtenerUsuariosPorIdsUseCase(ids) { usuarios ->
            _usuariosInscriptos.value = usuarios
        }
    }

    fun alternarParticipacion(context: Context, onResult: (Boolean) -> Unit) {
        val viaje = _viajeState.value ?: return
        val userId = SessionManager.obtenerUsuarioId(context) ?: return
        val yaParticipa = viaje.idPasajeros.contains(userId)

        participarEnViajeUseCase.execute(viaje.id, userId, !yaParticipa) { success ->
            if (success) {
                val nuevaLista = if (yaParticipa) {
                    viaje.idPasajeros.filter { it != userId }
                } else {
                    viaje.idPasajeros + userId
                }
                val viajeActualizado = viaje.copy(idPasajeros = nuevaLista)
                _viajeState.value = viajeActualizado
                cargarUsuariosInscriptos(nuevaLista)
            }
            onResult(success)
        }
    }
}
