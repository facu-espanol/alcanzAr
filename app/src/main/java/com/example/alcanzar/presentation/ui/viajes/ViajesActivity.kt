package com.example.alcanzar.presentation.ui.viajes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity
import com.example.alcanzar.presentation.ui.crearviaje.CrearViajeActivity
import com.example.alcanzar.presentation.ui.detalleviaje.DetalleViajeActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.viewmodel.ViajesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class ViajesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = ViajeFirestoreDataSource(FirebaseFirestore.getInstance())
        val repository = ViajeRepositoryImpl(dataSource)
        val vm = ViajesViewModel(
            ObtenerViajesUseCase(repository)
        )

        setContent {
            AlcanzARTheme {

                val uiState by vm.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    vm.cargarViajes()
                }

                ViajesScreen(
                    viajes = uiState.viajes,
                    onRefresh = {
                        vm.cargarViajes()
                    },
                    onViajeClick = { viaje ->
                        DetalleViajeActivity.viajeSeleccionado = viaje
                        startActivity(Intent(this, DetalleViajeActivity::class.java))
                    },
                    onConductorClick = { conductorId ->
                        val intent = Intent(this, PerfilActivity::class.java).apply {
                            putExtra("USER_ID", conductorId)
                        }
                        startActivity(intent)
                    },
                    onPerfilClick = {
                        startActivity(
                            Intent(this, PerfilActivity::class.java)
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
                    },

                    onPeticionesClick = {
                        startActivity(
                            Intent(this, PeticionesActivity::class.java)
                        )
                    },
                    onCrearViajeClick = {
                        startActivity(
                            Intent(this, CrearViajeActivity::class.java)
                        )
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
