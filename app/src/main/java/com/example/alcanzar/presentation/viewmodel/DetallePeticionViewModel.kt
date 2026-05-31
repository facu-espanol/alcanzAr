package com.example.alcanzar.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.usecase.PostularseAPeticionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetallePeticionViewModel(
    private val postularseAPeticionUseCase: PostularseAPeticionUseCase
) : ViewModel() {

    private val _peticionState = MutableStateFlow<Peticion?>(null)
    val peticionState = _peticionState.asStateFlow()

    fun setPeticion(peticion: Peticion) {
        _peticionState.value = peticion
    }

    fun alternarPostulacion(context: Context, onResult: (Boolean) -> Unit) {
        val peticion = _peticionState.value ?: return
        val userId = SessionManager.obtenerUsuarioId(context) ?: return
        val yaPostulado = peticion.idCandidatos.contains(userId)

        postularseAPeticionUseCase.execute(peticion.id, userId, !yaPostulado) { success ->
            if (success) {
                val nuevaLista = if (yaPostulado) {
                    peticion.idCandidatos.filter { it != userId }
                } else {
                    peticion.idCandidatos + userId
                }
                _peticionState.value = peticion.copy(idCandidatos = nuevaLista)
            }
            onResult(success)
        }
    }
}
