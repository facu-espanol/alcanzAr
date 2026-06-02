package com.example.alcanzar.domain.repository

import android.net.Uri

interface StorageRepository {
    fun subirFotoPerfil(uri: Uri, onResult: (String?) -> Unit)
}
