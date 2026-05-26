package com.example.alcanzar.presentation.ui.viajes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Viaje

@Composable
fun ViajeCard(
    viaje: Viaje
) {
    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {
        Row(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    "A: ${viaje.destino}",
                    fontSize = 20.sp
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Text(
                    "Hora salida: ${viaje.horaSalida}"
                )

                Text(
                    "Plazas: ${viaje.plazas}"
                )
            }

            Spacer(
                Modifier.width(12.dp)
            )

            Icon(
                imageVector =
                    if (viaje.vehiculo == "moto")
                        Icons.Default.DirectionsBike
                    else
                        Icons.Default.DirectionsCar,

                contentDescription = null,
                modifier =
                    Modifier.size(68.dp)
            )
        }
    }
}