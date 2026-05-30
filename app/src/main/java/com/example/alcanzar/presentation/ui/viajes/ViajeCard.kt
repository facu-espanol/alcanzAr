package com.example.alcanzar.presentation.ui.viajes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Viaje

@Composable
fun ViajeCard(
    viaje: Viaje
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.padding(16.dp)
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "A: ${viaje.ciudadDestino}",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Hora salida: ${viaje.horaSalida}"
                )

                Text(
                    text = "Fecha: ${viaje.fecha}"
                )

                Text(
                    text = "Plazas: ${viaje.plazas}"
                )

                Text(
                    text = "Costo sugerido: $${viaje.costoSugerido}"
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Icon(
                imageVector =
                    if (viaje.vehiculo.lowercase() == "moto")
                        Icons.Default.DirectionsBike
                    else
                        Icons.Default.DirectionsCar,

                contentDescription = null,

                modifier = Modifier.size(68.dp)
            )
        }
    }
}