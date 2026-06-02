package com.example.alcanzar.presentation.ui.viajes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.ui.theme.AlcanzarPrimary
import com.example.alcanzar.ui.theme.AlcanzarTextSecondary

@Composable
fun ViajeCard(
    viaje: Viaje,
    onClick: () -> Unit,
    onConductorClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hacia ${viaje.ciudadDestino}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AlcanzarPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Info del Conductor
                Row(
                    modifier = Modifier
                        .clickable { onConductorClick(viaje.conductorId) }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = AlcanzarPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = viaje.conductorNombre.ifBlank { "Conductor" },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = AlcanzarPrimary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${viaje.fecha} • ${viaje.horaSalida}",
                    fontSize = 14.sp,
                    color = AlcanzarTextSecondary
                )

                Text(
                    text = "Lugares: ${viaje.plazas} • $${viaje.costoSugerido}",
                    fontSize = 14.sp,
                    color = AlcanzarTextSecondary
                )
            }

            Icon(
                imageVector = if (viaje.vehiculo.lowercase() == "moto")
                    Icons.Default.DirectionsBike
                else
                    Icons.Default.DirectionsCar,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = AlcanzarPrimary.copy(alpha = 0.7f)
            )
        }
    }
}
