package com.example.alcanzar.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.alcanzar.domain.usecase.CrearCuentaUseCase
import com.example.alcanzar.domain.usecase.IniciarSesionUseCase
import com.example.alcanzar.domain.usecase.SubirImagenUseCase

class LoginViewModel(
    private val iniciarSesionUseCase: IniciarSesionUseCase,
    private val crearCuentaUseCase: CrearCuentaUseCase,
    private val subirImagenUseCase: SubirImagenUseCase
) : ViewModel() {

    fun iniciarSesion(usuario: String, password: String, onResult: (String?) -> Unit) {
        iniciarSesionUseCase(usuario, password, onResult)
    }

    fun crearCuenta(
        usuario: String, 
        password: String, 
        nombreCompleto: String, 
        fotoUri: Uri?, 
        onResult: (String?) -> Unit
    ) {
        if (fotoUri != null) {
            Log.d("LoginViewModel", "Subiendo imagen antes de crear cuenta...")
            subirImagenUseCase(fotoUri) { downloadUrl ->
                if (downloadUrl != null) {
                    Log.d("LoginViewModel", "Imagen subida, creando usuario en Firestore...")
                    crearCuentaUseCase(usuario, password, nombreCompleto, downloadUrl, onResult)
                } else {
                    Log.e("LoginViewModel", "Fallo la subida de imagen. No se creará la cuenta.")
                    onResult(null) // Esto hará que el Registro Activity muestre error
                }
            }
        } else {
            Log.d("LoginViewModel", "Sin imagen seleccionada, creando usuario directamente...")
            crearCuentaUseCase(usuario, password, nombreCompleto, "", onResult)
        }
    }
}
