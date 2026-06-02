package com.example.alcanzar.data

import android.net.Uri
import com.example.alcanzar.data.datasource.FirebaseStorageDataSource
import com.example.alcanzar.domain.repository.StorageRepository

class StorageRepositoryImpl(
    private val dataSource: FirebaseStorageDataSource
) : StorageRepository {
    override fun subirFotoPerfil(uri: Uri, onResult: (String?) -> Unit) {
        dataSource.subirFotoPerfil(uri, onResult)
    }
}
