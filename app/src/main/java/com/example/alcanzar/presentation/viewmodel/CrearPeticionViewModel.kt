package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.usecase.GuardarPeticionUseCase

class CrearPeticionViewModel(
    private val guardarPeticionUseCase: GuardarPeticionUseCase
) : ViewModel() {

    fun guardarPeticion(
        peticion: Peticion
    ) {
        guardarPeticionUseCase(peticion)
    }
}