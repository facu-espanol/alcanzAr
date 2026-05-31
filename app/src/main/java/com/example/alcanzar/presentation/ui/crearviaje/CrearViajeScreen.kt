import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Viaje
import com.example.alcanzar.presentation.ui.components.AppDrawer
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearViajeScreen(
    onPerfilClick: () -> Unit,
    onInicioClick: () -> Unit,
    onPeticionesClick: () -> Unit,
    onViajesClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onPublicarClick: (Viaje) -> Unit,
    onCrearPeticionClick: () -> Unit
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var paisOrigen by remember { mutableStateOf("") }
    var ciudadOrigen by remember { mutableStateOf("") }
    var paisDestino by remember { mutableStateOf("") }
    var ciudadDestino by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var vehiculo by remember { mutableStateOf("Auto") }
    var plazas by remember { mutableStateOf("1") }

    // El DatePicker
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // El TimePicker
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0,
        is24Hour = false
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        fecha = formatter.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        DatePickerDialog( // Se reusa el Reusing dialog container para consistencia
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    cal.set(Calendar.MINUTE, timePickerState.minute)
                    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    hora = formatter.format(cal.time)
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancelar") }
            }
        ) {
            Box(modifier = Modifier.padding(24.dp)) {
                TimePicker(state = timePickerState)
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onInicioClick = {
                    scope.launch {
                        drawerState.close()
                        onInicioClick()
                    }
                },
                onPeticionesClick = {
                    scope.launch {
                        drawerState.close()
                        onPeticionesClick()
                    }
                },
                onViajesClick = {
                    scope.launch {
                        drawerState.close()
                        onViajesClick()
                    }
                },
                onAcercaClick = {
                    scope.launch {
                        drawerState.close()
                        onAcercaClick()
                    }
                },
                onCrearViajeClick = {
                    scope.launch {
                        drawerState.close()
                    }
                },
                onCrearPeticionClick = {
                    scope.launch {
                        drawerState.close()
                        onCrearPeticionClick()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AlcanzAR", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = onPerfilClick) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
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
                    .padding(16.dp)
            ) {
                Text(text = "Crear viaje", fontSize = 22.sp)
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = paisOrigen,
                    onValueChange = { paisOrigen = it },
                    label = { Text("País origen") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = ciudadOrigen,
                    onValueChange = { ciudadOrigen = it },
                    label = { Text("Ciudad origen") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = paisDestino,
                    onValueChange = { paisDestino = it },
                    label = { Text("País destino") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = ciudadDestino,
                    onValueChange = { ciudadDestino = it },
                    label = { Text("Ciudad destino") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = fecha,
                    onValueChange = { },
                    label = { Text("Fecha") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = hora,
                    onValueChange = { },
                    label = { Text("Hora salida") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker = true },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    placeholder = { Text("Seleccionar hora") }
                )
                Spacer(Modifier.height(20.dp))

                Text("Vehículo")
                Row {
                    RadioButton(selected = vehiculo == "Auto", onClick = { vehiculo = "Auto" })
                    Text("Auto", modifier = Modifier.padding(top = 12.dp))
                    Spacer(Modifier.width(20.dp))
                    RadioButton(
                        selected = vehiculo == "Moto",
                        onClick = {
                            vehiculo = "Moto"
                            plazas = "1"
                        }
                    )
                    Text("Moto", modifier = Modifier.padding(top = 12.dp))
                }
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = plazas,
                    onValueChange = {
                        if (vehiculo == "Moto") plazas = "1" else plazas = it
                    },
                    label = { Text("Plazas disponibles") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(16.dp))

                Text(text = "Costo sugerido por asiento")
                Text(text = "$2500", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (paisOrigen.isBlank() || ciudadOrigen.isBlank() || 
                            paisDestino.isBlank() || ciudadDestino.isBlank() || 
                            fecha.isBlank() || hora.isBlank()) {
                            Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val viaje = Viaje(
                            paisOrigen = paisOrigen,
                            ciudadOrigen = ciudadOrigen,
                            paisDestino = paisDestino,
                            ciudadDestino = ciudadDestino,
                            fecha = fecha,
                            horaSalida = hora,
                            vehiculo = vehiculo,
                            plazas = plazas.toIntOrNull() ?: 1,
                            costoSugerido = 2500.0
                        )
                        onPublicarClick(viaje)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    Text("Publicar viaje")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}