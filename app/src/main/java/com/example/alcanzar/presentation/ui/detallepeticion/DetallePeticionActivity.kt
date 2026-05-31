package com.example.alcanzar.presentation.ui.detallepeticion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.alcanzar.data.PeticionRepositoryImpl
import com.example.alcanzar.data.datasource.PeticionFirestoreDataSource
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.usecase.PostularseAPeticionUseCase
import com.example.alcanzar.presentation.viewmodel.DetallePeticionViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class DetallePeticionActivity : ComponentActivity() {

    companion object {
        var peticionSeleccionada: Peticion? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = PeticionFirestoreDataSource(
            FirebaseFirestore.getInstance()
        )

        val repository = PeticionRepositoryImpl(dataSource)

        val viewModel = DetallePeticionViewModel(
            PostularseAPeticionUseCase(repository)
        )

        peticionSeleccionada?.let {
            viewModel.setPeticion(it)
        }

        setContent {
            AlcanzARTheme {

                val peticion by viewModel.peticionState.collectAsState()

                peticion?.let {

                    DetallePeticionScreen(
                        peticion = it,

                        onBack = {
                            finish()
                        },

                        onPostularse = {
                            viewModel.alternarPostulacion(this) { success ->

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