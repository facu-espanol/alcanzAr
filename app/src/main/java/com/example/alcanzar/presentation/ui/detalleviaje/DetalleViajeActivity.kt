package com.example.alcanzar.presentation.ui.detalleviaje

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.usecase.ParticiparEnViajeUseCase
import com.example.alcanzar.presentation.viewmodel.DetalleViajeViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class DetalleViajeActivity : ComponentActivity() {

    companion object {
        var viajeSeleccionado: Viaje? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource =
            ViajeFirestoreDataSource(
                FirebaseFirestore.getInstance()
            )

        val repository =
            ViajeRepositoryImpl(dataSource)

        val viewModel =
            DetalleViajeViewModel(
                ParticiparEnViajeUseCase(repository)
            )

        viajeSeleccionado?.let {
            viewModel.setViaje(it)
        }

        setContent {
            AlcanzARTheme {

                val viaje by
                viewModel.viajeState.collectAsState()

                viaje?.let {

                    DetalleViajeScreen(
                        viaje = it,

                        onBack = {
                            finish()
                        },

                        onParticipar = {
                            viewModel.alternarParticipacion(this) { success ->

                                if (success) {
                                    Toast.makeText(
                                        this,
                                        "Operación exitosa",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Error al procesar",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}