package com.example.alcanzar.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.GetUsuarioUseCase
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.presentation.statedata.PerfilUiState
import java.text.SimpleDateFormat
import java.util.*

class PerfilViewModel(
    private val getUsuario: GetUsuarioUseCase,
    private val obtenerViajes: ObtenerViajesUseCase
) : ViewModel() {

    var state by mutableStateOf(PerfilUiState())
        private set

    fun cargarPerfil(context: android.content.Context, userId: String? = null) {
        state = state.copy(loading = true)
        val currentSessionId = SessionManager.obtenerUsuarioId(context)
        val id = userId ?: currentSessionId

        if (id == null) {
            state = state.copy(loading = false, usuario = null)
            return
        }

        getUsuario(id) { usuario ->
            val finalUser = usuario ?: when {
                id == "admin_user" || id == "driver_test" -> Usuario(
                    id = id,
                    nombreCompleto = if (id == "admin_user") "Facundo (Admin)" else "Juan Perez (Test)",
                    promedioConductor = 4.8,
                    totalConductor = 22,
                    promedioPasajero = 5.0,
                    totalPasajero = 14
                )
                else -> null
            }

            state = state.copy(usuario = finalUser, loading = false)
            
            // SEGURIDAD: Solo cargamos viajes si es MI sesión
            if (id == currentSessionId) {
                cargarMisViajes(id)
            } else {
                state = state.copy(viajesPendientes = emptyList(), historialViajes = emptyList())
            }
        }
    }

    private fun cargarMisViajes(userId: String) {
        obtenerViajes.execute { todosLosViajes ->
            val misViajes = todosLosViajes.filter { it.conductorId == userId }
            
            val ahora = Calendar.getInstance().time

            val (pendientes, historial) = misViajes.partition { viaje ->
                esViajeFuturo(viaje, ahora)
            }
            
            state = state.copy(
                viajesPendientes = pendientes,
                historialViajes = historial
            )
        }
    }

    private fun esViajeFuturo(viaje: Viaje, ahora: Date): Boolean {
        return try {
            // Normalizamos AM/PM eliminando puntos y pasando a mayúsculas para mayor robustez
            val horaNormalizada = viaje.horaSalida.replace(".", "").uppercase().trim()
            val fechaHoraString = "${viaje.fecha} $horaNormalizada"
            
            // Intentamos primero con Locale.US para el AM/PM normalizado
            val sdfUS = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US)
            val fechaHoraViaje = sdfUS.parse(fechaHoraString)
            fechaHoraViaje != null && fechaHoraViaje.after(ahora)
        } catch (e: Exception) {
            try {
                // Si falla, intentamos con el Locale por defecto
                val sdfDefault = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
                val fechaHoraViaje = sdfDefault.parse("${viaje.fecha} ${viaje.horaSalida}")
                fechaHoraViaje != null && fechaHoraViaje.after(ahora)
            } catch (e2: Exception) {
                // En caso de error de parsing, si la fecha es hoy, lo dejamos pendiente por precaución
                true
            }
        }
    }
}
