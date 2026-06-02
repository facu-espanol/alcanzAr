package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.CrearCuentaUseCase
import com.example.alcanzar.domain.usecase.IniciarSesionUseCase

class LoginViewModel(
    private val iniciarSesionUseCase: IniciarSesionUseCase,
    private val crearCuentaUseCase: CrearCuentaUseCase
) : ViewModel() {

    fun iniciarSesion(usuario: String, password: String, onResult: (String?) -> Unit) {
        iniciarSesionUseCase(usuario, password, onResult)
    }

    fun crearCuenta(
        usuario: String, 
        password: String, 
        nombreCompleto: String, 
        fotoBase64: String, 
        onResult: (String?) -> Unit
    ) {
        // Ahora guardamos la imagen comprimida directamente en Firestore
        crearCuentaUseCase(usuario, password, nombreCompleto, fotoBase64, onResult)
    }
}
