package com.example.alcanzar.presentation.ui.crearpeticion

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.presentation.ui.components.AppDrawer
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPeticionScreen(
    onPerfilClick: () -> Unit,
    onInicioClick: () -> Unit,
    onPeticionesClick: () -> Unit,
    onViajesClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onPublicarClick: (Peticion) -> Unit,
    onCrearPeticionClick: () -> Unit,
    onCrearViajeClick: () -> Unit,
    onNotificacionesClick: () -> Unit,
    onCerrarSesionClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var barrio by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

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
        DatePickerDialog(
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
                    scope.launch { drawerState.close(); onInicioClick() }
                },
                onPeticionesClick = {
                    scope.launch { drawerState.close(); onPeticionesClick() }
                },
                onViajesClick = {
                    scope.launch { drawerState.close(); onViajesClick() }
                },
                onAcercaClick = {
                    scope.launch { drawerState.close(); onAcercaClick() }
                },
                onCrearViajeClick = {
                    scope.launch { drawerState.close(); onCrearViajeClick() }
                },
                onCrearPeticionClick = {
                    scope.launch { drawerState.close() }
                },
                onMiPerfilClick = {
                    scope.launch { drawerState.close(); onPerfilClick() }
                },
                onNotificacionesClick = {
                    scope.launch { drawerState.close(); onNotificacionesClick() }
                },
                onCerrarSesionClick = {
                    scope.launch { drawerState.close(); onCerrarSesionClick() }
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
                Text(text = "Crear petición", fontSize = 22.sp)
                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = pais,
                    onValueChange = { pais = it },
                    label = { Text("País") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = barrio,
                    onValueChange = { barrio = it },
                    label = { Text("Barrio") },
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
                    label = { Text("Hora") },
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

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (titulo.isBlank() || descripcion.isBlank() || 
                            pais.isBlank() || ciudad.isBlank() || 
                            barrio.isBlank() || fecha.isBlank() || hora.isBlank()) {
                            Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val peticion = Peticion(
                            titulo = titulo,
                            descripcion = descripcion,
                            pais = pais,
                            ciudad = ciudad,
                            barrio = barrio,
                            fecha = fecha,
                            hora = hora
                        )
                        onPublicarClick(peticion)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Publicar petición")
                }
            }
        }
    }
}
