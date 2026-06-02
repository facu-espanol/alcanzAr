package com.example.alcanzar.presentation.ui.calificacion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.presentation.rating.CalificacionViewModel

@Composable
fun CalificacionScreen(
    viewModel: CalificacionViewModel,
    viaje: Viaje,
    miId: String
) {

    val estado = viewModel.estado

    val esConductor = viaje.conductorId == miId

    val haciaUsuarioId = if (esConductor) {
        viaje.idPasajeros.firstOrNull() ?: ""
    } else {
        viaje.conductorId
    }

    Column {

        Text(
            text = if (esConductor)
                "Calificar pasajero"
            else
                "Calificar conductor"
        )

        Row {
            (1..5).forEach { i ->
                TextButton(onClick = { viewModel.onPuntaje(i) }) {
                    Text(if (estado.puntaje >= i) "★" else "☆")
                }
            }
        }

        OutlinedTextField(
            value = estado.comentario,
            onValueChange = viewModel::onComentario,
            label = { Text("Comentario") }
        )

        Button(
            onClick = {
                viewModel.enviar(
                    desde = miId,
                    hacia = haciaUsuarioId,
                    viajeId = viaje.id,
                    rol = if (esConductor) "PASAJERO" else "CONDUCTOR"
                )
            }
        ) {
            Text("Enviar")
        }
    }
}