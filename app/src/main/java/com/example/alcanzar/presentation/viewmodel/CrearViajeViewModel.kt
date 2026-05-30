package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.usecase.GuardarViajeUseCase

class CrearViajeViewModel(
    private val guardarViajeUseCase: GuardarViajeUseCase
) : ViewModel() {

    fun guardarViaje(viaje: Viaje) {
        guardarViajeUseCase(viaje)
    }
}