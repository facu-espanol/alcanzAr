package com.example.alcanzar.data

import android.content.Context
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.repository.PeticionRepository

class PeticionRepositoryImpl(
    private val context: Context
) : PeticionRepository {

    override fun obtenerPeticiones(): List<Peticion> {
        val lista = mutableListOf<Peticion>()

        context.assets.open("peticiones.txt")
            .bufferedReader()
            .use { reader ->
                reader.forEachLine { linea ->
                    if (linea.isNotBlank()) {
                        val partes = linea.split(";")

                        if (partes.size >= 5) {
                            lista.add(
                                Peticion(
                                    tipo = partes[0],
                                    desde = partes[1],
                                    destino = partes[2],
                                    horario = partes[3],
                                    extras = partes[4]
                                )
                            )
                        }
                    }
                }
            }

        return lista
    }
}