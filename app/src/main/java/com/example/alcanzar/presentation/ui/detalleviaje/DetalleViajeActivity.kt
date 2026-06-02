package com.example.alcanzar.presentation.ui.detalleviaje

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.data.ViajeRepositoryImpl
import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.data.datasource.ViajeFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.domain.usecase.ObtenerUsuariosPorIdsUseCase
import com.example.alcanzar.domain.usecase.ParticiparEnViajeUseCase
import com.example.alcanzar.presentation.ui.perfil.PerfilActivity
import com.example.alcanzar.presentation.viewmodel.DetalleViajeViewModel
import com.example.alcanzar.presentation.viewmodel.ParticipacionResult
import com.example.alcanzar.presentation.ui.components.UserAvatar
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.example.alcanzar.ui.theme.AlcanzarPrimary
import com.google.firebase.firestore.FirebaseFirestore

class DetalleViajeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viajeSeleccionado = intent.getSerializableExtra("VIAJE") as? Viaje

        // Inyección manual con la instancia de Firestore
        val db = FirebaseFirestore.getInstance()
        val userDataSource = UserFirestoreDataSource(db)
        val viajeDataSource = ViajeFirestoreDataSource(db)

        val viajeRepository = ViajeRepositoryImpl(viajeDataSource)
        val loginRepository = LoginRepositoryImpl(userDataSource)

        val viewModel = DetalleViajeViewModel(
            ParticiparEnViajeUseCase(viajeRepository),
            ObtenerUsuariosPorIdsUseCase(loginRepository)
        )
        viajeSeleccionado?.let { viewModel.setViaje(it) }

        setContent {
            AlcanzARTheme {
                val viaje by viewModel.viajeState.collectAsState()
                val usuariosInscriptos by viewModel.usuariosInscriptos.collectAsState()
                val conductor by viewModel.conductor.collectAsState()

                viaje?.let {
                    DetalleViajeContent(
                        viaje = it,
                        conductor = conductor,
                        inscriptos = usuariosInscriptos,
                        onBack = { finish() },
                        onParticipar = {
                            viewModel.alternarParticipacion(this) { result ->
                                when (result) {
                                    ParticipacionResult.Success -> {
                                        Toast.makeText(this, "Operación exitosa", Toast.LENGTH_SHORT).show()
                                    }
                                    ParticipacionResult.SinCupos -> {
                                        Toast.makeText(this, "No quedan plazas disponibles", Toast.LENGTH_LONG).show()
                                    }
                                    ParticipacionResult.Error -> {
                                        Toast.makeText(this, "Error al procesar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleViajeContent(
    viaje: Viaje,
    conductor: Usuario?,
    inscriptos: List<Usuario>,
    onBack: () -> Unit,
    onParticipar: () -> Unit
) {
    val context = LocalContext.current
    val userId = SessionManager.obtenerUsuarioId(context) ?: ""
    val yaParticipa = viaje.idPasajeros.contains(userId)
    val esCreador = viaje.conductorId == userId
    val estaLleno = viaje.idPasajeros.size >= viaje.plazas

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Viaje", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AlcanzarPrimary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Card Principal de Información
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    DetailItem(Icons.Default.LocationOn, "Origen", "${viaje.ciudadOrigen}, ${viaje.paisOrigen}")
                    DetailItem(Icons.Default.LocationOn, "Destino", "${viaje.ciudadDestino}, ${viaje.paisDestino}")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        DetailItem(Icons.Default.Event, "Fecha", viaje.fecha, Modifier.weight(1f))
                        DetailItem(Icons.Default.Schedule, "Hora", viaje.horaSalida, Modifier.weight(1f))
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        DetailItem(Icons.Default.DirectionsCar, "Vehículo", viaje.vehiculo, Modifier.weight(1f))
                        DetailItem(Icons.Default.Group, "Plazas", "${viaje.plazas}", Modifier.weight(1f))
                    }
                    DetailItem(Icons.Default.Payments, "Costo sugerido", "$${viaje.costoSugerido}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección del Conductor
            Text(
                text = "Conductor",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AlcanzarPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(context, PerfilActivity::class.java).apply {
                            putExtra("USER_ID", viaje.conductorId)
                        }
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AlcanzarPrimary.copy(alpha = 0.08f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserAvatar(fotoUrl = conductor?.fotoUrl ?: "", modifier = Modifier.size(50.dp))
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = conductor?.nombreCompleto ?: "Cargando...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = AlcanzarPrimary
                        )
                        Text(
                            text = "Toca para ver perfil y reputación",
                            fontSize = 12.sp,
                            color = AlcanzarPrimary.copy(alpha = 0.7f)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = AlcanzarPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de Inscriptos / Pasajeros
            Text(
                text = "Pasajeros inscriptos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AlcanzarPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (esCreador) {
                if (inscriptos.isEmpty()) {
                    Text("Aún no hay pasajeros inscriptos", fontSize = 14.sp, color = Color.Gray)
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        items(inscriptos) { usuario ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, PerfilActivity::class.java).apply {
                                        putExtra("USER_ID", usuario.id)
                                    }
                                    context.startActivity(intent)
                                }
                            ) {
                                UserAvatar(fotoUrl = usuario.fotoUrl)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = usuario.nombreCompleto,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier.width(80.dp),
                                    maxLines = 2,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            } else {
                Surface(
                    color = if (estaLleno) MaterialTheme.colorScheme.errorContainer else AlcanzarPrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Group,
                            null,
                            modifier = Modifier.size(20.dp),
                            tint = if (estaLleno) MaterialTheme.colorScheme.error else AlcanzarPrimary
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (estaLleno) "Viaje completo" else "${viaje.idPasajeros.size}/${viaje.plazas} personas anotadas",
                            fontWeight = FontWeight.Bold,
                            color = if (estaLleno) MaterialTheme.colorScheme.error else AlcanzarPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de Acción
            if (!esCreador) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onParticipar,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        enabled = yaParticipa || !estaLleno,
                        colors = if (yaParticipa)
                            ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        else if (estaLleno)
                            ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                        else
                            ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = when {
                                yaParticipa -> "DARSE DE BAJA"
                                estaLleno -> "VIAJE COMPLETO"
                                else -> "SUMARSE AL VIAJE"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    if (yaParticipa) {
                        FloatingActionButton(
                            onClick = { /* Chat logic */ },
                            containerColor = AlcanzarPrimary,
                            contentColor = Color.White,
                            modifier = Modifier.size(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.Chat, "Chat")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = AlcanzarPrimary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 11.sp, color = Color.Gray)
            Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
