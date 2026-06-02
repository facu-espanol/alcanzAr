package com.example.alcanzar.presentation.rating

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcanzar.domain.model.Calificacion
import com.example.alcanzar.domain.usecase.CrearCalificacionUseCase
import com.example.alcanzar.presentation.state.EstadoCalificacion
import kotlinx.coroutines.launch

class CalificacionViewModel(
    private val crearCalificacion: CrearCalificacionUseCase
) : ViewModel() {

    var estado by mutableStateOf(EstadoCalificacion())
        private set

    fun onPuntaje(p: Int) {
        estado = estado.copy(puntaje = p)
    }

    fun onComentario(c: String) {
        estado = estado.copy(comentario = c)
    }

    fun enviar(
        desde: String,
        hacia: String,
        viajeId: String,
        rol: String
    ) {
        viewModelScope.launch {

            estado = estado.copy(cargando = true)

            try {
                crearCalificacion(
                    Calificacion(
                        desdeUsuarioId = desde,
                        haciaUsuarioId = hacia,
                        viajeId = viajeId,
                        rol = rol,
                        puntaje = estado.puntaje,
                        comentario = estado.comentario
                    )
                )

                estado = estado.copy(exito = true)

            } catch (e: Exception) {
                estado = estado.copy(error = e.message)

            } finally {
                estado = estado.copy(cargando = false)
            }
        }
    }
}