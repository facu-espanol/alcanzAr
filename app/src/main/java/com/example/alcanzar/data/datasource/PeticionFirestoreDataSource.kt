package com.example.alcanzar.data.datasource

import com.example.alcanzar.domain.model.Peticion
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class PeticionFirestoreDataSource(
    private val db: FirebaseFirestore
) {
    fun guardarPeticion(peticion: Peticion, onResult: (Boolean) -> Unit) {
        db.collection("peticiones")
            .add(peticion)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun obtenerPeticiones(onResult: (List<Peticion>) -> Unit) {
        db.collection("peticiones")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val peticiones = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Peticion::class.java)?.copy(id = doc.id)
                }
                onResult(peticiones)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    fun postularseAPeticion(peticionId: String, userId: String, postularse: Boolean, onResult: (Boolean) -> Unit) {
        val op = if (postularse) FieldValue.arrayUnion(userId) else FieldValue.arrayRemove(userId)
        db.collection("peticiones").document(peticionId)
            .update("idCandidatos", op)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
