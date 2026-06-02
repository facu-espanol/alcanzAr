package com.example.alcanzar.presentation.ui.viajes

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.ObtenerUsuariosPorIdsUseCase
import com.example.alcanzar.domain.usecase.ObtenerViajesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.crearpeticion.CrearPeticionActivity
import com.example.alcanzar.presentation.ui.crearviaje.CrearViajeActivity
import com.example.alcanzar.presentation.ui.detalleviaje.DetalleViajeActivity
import com.example.alcanzar.presentation.ui.login.LoginActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.perfil.SesionActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.viewmodel.ViajesViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme

class ViajesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        
        val viajeDataSource = ViajeFirestoreDataSource(db)
        val viajeRepository = ViajeRepositoryImpl(viajeDataSource)
        
        val userDataSource = UserFirestoreDataSource(db)
        val loginRepository = LoginRepositoryImpl(userDataSource)

        val vm = ViajesViewModel(
            ObtenerViajesUseCase(viajeRepository),
            ObtenerUsuariosPorIdsUseCase(loginRepository)
        )

        setContent {
            AlcanzARTheme {

                val uiState by vm.uiState.collectAsState()
                val currentUserId = SessionManager.obtenerUsuarioId(this)

                LaunchedEffect(Unit) {
                    vm.cargarViajes(currentUserId)
                }

                ViajesScreen(
                    viajes = uiState.viajes,
                    usuariosConductores = uiState.usuariosConductores,
                    onRefresh = {
                        vm.cargarViajes(currentUserId)
                    },
                    onViajeClick = { viaje ->
                        val intent = Intent(this, DetalleViajeActivity::class.java).apply {
                            putExtra("VIAJE", viaje)
                        }
                        startActivity(intent)
                    },
                    onConductorClick = { conductorId ->
                        val intent = Intent(this, PerfilActivity::class.java).apply {
                            putExtra("USER_ID", conductorId)
                        }
                        startActivity(intent)
                    },
                    onPerfilClick = {
                        // Navegación a MI SESIÓN privada
                        startActivity(
                            Intent(this, SesionActivity::class.java)
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
