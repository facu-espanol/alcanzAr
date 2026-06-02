package com.example.alcanzar.presentation.ui.crearpeticion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alcanzar.data.PeticionRepositoryImpl
import com.example.alcanzar.data.datasource.PeticionFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.usecase.GuardarPeticionUseCase
import com.example.alcanzar.presentation.ui.acerca.AcercaActivity
import com.example.alcanzar.presentation.ui.bienvenida.BienvenidaActivity
import com.example.alcanzar.presentation.ui.crearviaje.CrearViajeActivity
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.ui.peticiones.PeticionesActivity
import com.example.alcanzar.presentation.ui.viajes.ViajesActivity
import com.example.alcanzar.presentation.viewmodel.CrearPeticionViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class CrearPeticionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val dataSource = PeticionFirestoreDataSource(db)
        val repository = PeticionRepositoryImpl(dataSource)
        val useCase = GuardarPeticionUseCase(repository)
        val vm = CrearPeticionViewModel(useCase)

        setContent {
            AlcanzARTheme {
                CrearPeticionScreen(
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

                    onCrearPeticionClick = {
                        // estoy en CrearPeticionActivity
                    },

                    onPublicarClick = { peticion ->
                        val userId = SessionManager.obtenerUsuarioId(this) ?: ""
                        val peticionConCreador = peticion.copy(idCreador = userId)

                        vm.guardarPeticion(peticionConCreador) { success ->
                            if (success) {
                                Toast.makeText(
                                    this,
                                    "Petición publicada",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(this, PeticionesActivity::class.java)
                                )
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al publicar petición",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    onCrearViajeClick = {
                        startActivity(
                            Intent(this, CrearViajeActivity::class.java)
                        )
                    },
                )
            }
        }
    }
}
