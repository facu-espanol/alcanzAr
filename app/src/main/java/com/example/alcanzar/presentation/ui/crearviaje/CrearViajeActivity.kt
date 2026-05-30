package com.example.alcanzar.presentation.ui.crearviaje

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.domain.usecase.GuardarViajeUseCase
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.CrearViajeViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import android.util.Log
import android.widget.Toast
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity

class CrearViajeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ViajeRepositoryImpl()

        val guardarViajeUseCase =
            GuardarViajeUseCase(repository)

        val vm =
            CrearViajeViewModel(guardarViajeUseCase)

        setContent {
            AlcanzARTheme {

                CrearViajeScreen(

                    onPerfilClick = {
                        startActivity(
                            Intent(this, PerfilActivity::class.java)
                        )
                    },

                    onInicioClick = {
                        startActivity(
                            Intent(this, BienvenidaActivity::class.java)
                        )
                    },

                    onPeticionesClick = {
                        startActivity(
                            Intent(this, PeticionesActivity::class.java)
                        )
                    },

                    onViajesClick = {
                        startActivity(
                            Intent(this, ViajesActivity::class.java)
                        )
                    },

                    onAcercaClick = {
                        startActivity(
                            Intent(this, AcercaActivity::class.java)
                        )
                    },

                    onPublicarClick = { viaje ->

                        vm.guardarViaje(viaje)

                        Toast.makeText(
                            this,
                            "Viaje publicado",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this, ViajesActivity::class.java)
                        )

                        finish()
                    },
                    onCrearPeticionClick = {
                        startActivity(
                            Intent(
                                this,
                                CrearPeticionActivity::class.java
                            )
                        )
                    },

                    )
            }
        }
    }
}