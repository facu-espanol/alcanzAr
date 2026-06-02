package com.example.alcanzar.presentation.ui.peticiones

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.PeticionRepositoryImpl
import com.example.alcanzar.data.datasource.PeticionFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.ObtenerPeticionesUseCase
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity
import com.example.alcanzar.presentation.ui.crearviaje.CrearViajeActivity
import com.example.alcanzar.presentation.ui.detallepeticion.DetallePeticionActivity
import com.example.alcanzar.presentation.ui.login.LoginActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.PeticionesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class PeticionesActivity : ComponentActivity() {

    private lateinit var viewModel: PeticionesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = PeticionFirestoreDataSource(FirebaseFirestore.getInstance())
        val repository = PeticionRepositoryImpl(dataSource)
        val useCase = ObtenerPeticionesUseCase(repository)
        viewModel = PeticionesViewModel(useCase)

        setContent {
            AlcanzARTheme {
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    viewModel.cargarPeticiones()
                }

                PeticionesScreen(
                    peticiones = uiState.peticiones,
                    onRefresh = {
                        viewModel.cargarPeticiones()
                    },
                    onPeticionClick = { peticion ->
                        DetallePeticionActivity.peticionSeleccionada = peticion
                        startActivity(Intent(this, DetallePeticionActivity::class.java))
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

                    onViajesClick = {
                        startActivity(
                            Intent(this, ViajesActivity::class.java)
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
                    onCerrarSesionClick = {
                        SessionManager.cerrarSesion(this)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
