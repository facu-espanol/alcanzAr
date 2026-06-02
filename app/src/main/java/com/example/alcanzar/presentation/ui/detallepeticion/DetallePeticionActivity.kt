package com.example.alcanzar.presentation.ui.detallepeticion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.alcanzar.data.LoginRepositoryImpl
import com.example.alcanzar.data.PeticionRepositoryImpl
import com.example.alcanzar.data.datasource.PeticionFirestoreDataSource
import com.example.alcanzar.data.datasource.UserFirestoreDataSource
import com.example.alcanzar.data.session.SessionManager
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.domain.model.Usuario
import com.example.alcanzar.domain.usecase.ObtenerUsuariosPorIdsUseCase
import com.example.alcanzar.domain.usecase.PostularseAPeticionUseCase
import com.example.alcanzar.presentation.ui.components.DetailItem
import com.example.alcanzar.presentation.ui.components.UserAvatar
import com.example.alcanzar.presentation.viewmodel.DetallePeticionViewModel
import com.example.alcanzar.ui.theme.AlcanzARTheme
import com.google.firebase.firestore.FirebaseFirestore

class DetallePeticionActivity : ComponentActivity() {

    companion object {
        var peticionSeleccionada: Peticion? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val peticionDataSource = PeticionFirestoreDataSource(db)
        val userDataSource = UserFirestoreDataSource(db)

        val peticionRepository = PeticionRepositoryImpl(peticionDataSource)
        val loginRepository = LoginRepositoryImpl(userDataSource)

        val viewModel = DetallePeticionViewModel(
            PostularseAPeticionUseCase(peticionRepository),
            ObtenerUsuariosPorIdsUseCase(loginRepository)
        )

        peticionSeleccionada?.let { viewModel.setPeticion(it) }

        setContent {
            AlcanzARTheme {
                val peticion by viewModel.peticionState.collectAsState()
                val candidatos by viewModel.candidatos.collectAsState()

                peticion?.let {
                    DetallePeticionContent(
                        peticion = it,
                        candidatos = candidatos,
                        onBack = { finish() },
                        onPostularse = {
                            viewModel.alternarPostulacion(this) { success ->
                                if (success) {
                                    Toast.makeText(this, "Operación exitosa", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this, "Error al procesar", Toast.LENGTH_SHORT).show()
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
fun DetallePeticionContent(
    peticion: Peticion, 
    candidatos: List<Usuario>, 
    onBack: () -> Unit, 
    onPostularse: () -> Unit
) {
    val context = LocalContext.current
    val userId = SessionManager.obtenerUsuarioId(context) ?: ""
    val yaPostulado = peticion.idCandidatos.contains(userId)
    val esCreador = peticion.idCreador == userId

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Petición", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = peticion.titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    DetailItem(Icons.Default.Description, "Descripción", peticion.descripcion)
                    DetailItem(Icons.Default.LocationOn, "Ubicación", "${peticion.barrio}, ${peticion.ciudad}, ${peticion.pais}")
                    
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        DetailItem(Icons.Default.Event, "Fecha", peticion.fecha, Modifier.weight(1f))
                        DetailItem(Icons.Default.Schedule, "Hora", peticion.hora, Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de Candidatos
            Text(
                text = "Candidatos para ayudar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            if (esCreador) {
                if (candidatos.isEmpty()) {
                    Text("Aún no hay candidatos", fontSize = 14.sp, color = Color.Gray)
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        items(candidatos) { usuario ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Group, null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "${peticion.idCandidatos.size} personas se ofrecieron",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (!esCreador) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onPostularse,
                        modifier = Modifier.weight(1f),
                        colors = if (yaPostulado) 
                            ButtonDefaults.buttonColors(containerColor = Color.Gray) 
                        else 
                            ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            if (yaPostulado) "ANULAR POSTULACIÓN" else "POSTULARSE PARA AYUDAR",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (yaPostulado) {
                        FloatingActionButton(
                            onClick = { /* Chat */ },
                            containerColor = Color(0xFF1976D2),
                            contentColor = Color.White,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Chat, "Chat")
                        }
                    }
                }
            } else {
                Text(
                    "Sos el creador de esta petición",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun DetailItem(icon: ImageVector, label: String, value: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color(0xFF1976D2), modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}
