package com.example.alcanzar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.CrearCuentaUseCase
import com.example.alcanzar.domain.usecase.IniciarSesionUseCase

class LoginViewModel(
    private val iniciarSesionUseCase: IniciarSesionUseCase,
    private val crearCuentaUseCase: CrearCuentaUseCase
) : ViewModel() {

    fun iniciarSesion(usuario: String, password: String): Boolean {
        return iniciarSesionUseCase(usuario, password)
    }

    fun crearCuenta(usuario: String, password: String): Boolean {
        return crearCuentaUseCase(usuario, password)
    }
}