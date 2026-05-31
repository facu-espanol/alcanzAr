package com.example.alcanzar.presentation.ui.detallepeticion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.presentation.ui.components.DetailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePeticionScreen(
    peticion: Peticion,
    onBack: () -> Unit,
    onPostularse: () -> Unit
) {

    val context = LocalContext.current

    val userId =
        SessionManager.obtenerUsuarioId(context) ?: ""

    val yaPostulado =
        peticion.idCandidatos.contains(userId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle de la Petición",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = peticion.titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    DetailItem(
                        Icons.Default.Description,
                        "Descripción",
                        peticion.descripcion
                    )

                    DetailItem(
                        Icons.Default.LocationOn,
                        "Ubicación",
                        "${peticion.barrio}, ${peticion.ciudad}, ${peticion.pais}"
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 12.dp
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        DetailItem(
                            Icons.Default.Event,
                            "Fecha",
                            peticion.fecha,
                            Modifier.weight(1f)
                        )

                        DetailItem(
                            Icons.Default.Schedule,
                            "Hora",
                            peticion.hora,
                            Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = onPostularse,
                    modifier = Modifier.weight(1f),
                    colors =
                        if (yaPostulado)
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            )
                        else
                            ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                    shape = MaterialTheme.shapes.medium
                ) {

                    Text(
                        text =
                            if (yaPostulado)
                                "ANULAR POSTULACIÓN"
                            else
                                "POSTULARSE PARA AYUDAR",
                        fontWeight = FontWeight.Bold
                    )
                }

                if (yaPostulado) {

                    FloatingActionButton(
                        onClick = {
                            // chat futuro
                        },
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Chat,
                            contentDescription = "Chat"
                        )
                    }
                }
            }
        }
    }
}