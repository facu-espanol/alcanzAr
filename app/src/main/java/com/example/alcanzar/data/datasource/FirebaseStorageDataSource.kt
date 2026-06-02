package com.example.alcanzar.data.datasource

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FirebaseStorageDataSource(
    private val storage: FirebaseStorage
) {
    fun subirFotoPerfil(uri: Uri, onResult: (String?) -> Unit) {
        val fileName = "perfiles/${UUID.randomUUID()}.jpg"
        val ref = storage.reference.child(fileName)

        Log.d("FirebaseStorage", "Iniciando subida de: $uri")

        ref.putFile(uri)
            .addOnSuccessListener {
                Log.d("FirebaseStorage", "Subida exitosa, obteniendo URL...")
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    Log.d("FirebaseStorage", "URL obtenida: $downloadUri")
                    onResult(downloadUri.toString())
                }.addOnFailureListener { e ->
                    Log.e("FirebaseStorage", "Error al obtener URL", e)
                    onResult(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseStorage", "Error al subir archivo a Storage", e)
                onResult(null)
            }
    }
}