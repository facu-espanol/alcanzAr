package com.example.alcanzar.peticiones

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.acerca.AcercaActivity
import com.example.alcanzar.bienvenida.BienvenidaActivity
import com.example.alcanzar.perfil.Perfil

class PeticionesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lista = leerPeticiones()

        setContent {
            PeticionesScreen(
                peticiones = lista,
                onPerfilClick = {
                    startActivity(
                        Intent(this, Perfil::class.java)
                    )
                },
                onInicioClick = {
                    startActivity(
                        Intent(this, BienvenidaActivity::class.java)
                    )
                    finish()
                },
                onAcercaClick = {
                    startActivity(
                        Intent(this, AcercaActivity::class.java)
                    )
                }
            )
        }
    }

    private fun leerPeticiones(): List<Peticion> {
        val lista = mutableListOf<Peticion>()

        val inputStream =
            assets.open("peticiones.txt")

        val reader =
            inputStream.bufferedReader()

        reader.forEachLine { linea ->
            if (linea.isNotBlank()) {

                val partes =
                    linea.split(";")

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

        reader.close()
        return lista
    }
}