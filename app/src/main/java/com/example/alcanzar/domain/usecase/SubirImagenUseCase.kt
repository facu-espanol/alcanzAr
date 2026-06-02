package com.example.alcanzar.domain.usecase

import android.net.Uri
import com.example.alcanzar.domain.repository.StorageRepository

class SubirImagenUseCase(
    private val repository: StorageRepository
) {
    operator fun invoke(uri: Uri, onResult: (String?) -> Unit) {
        repository.subirFotoPerfil(uri, onResult)
    }
}
