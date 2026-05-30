package com.example.alcanzar.presentation.ui.crearpeticion

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Peticion
import com.example.alcanzar.presentation.ui.components.AppDrawer
import kotlinx.coroutines.launch

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
    onCrearViajeClick: () -> Unit

) {

    val drawerState =
        rememberDrawerState(
            DrawerValue.Closed
        )

    val scope =
        rememberCoroutineScope()

    var titulo by remember {
        mutableStateOf("")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    var pais by remember {
        mutableStateOf("")
    }

    var ciudad by remember {
        mutableStateOf("")
    }

    var barrio by remember {
        mutableStateOf("")
    }

    var fecha by remember {
        mutableStateOf("")
    }

    var hora by remember {
        mutableStateOf("")
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
                        onCrearViajeClick()
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

                    title = {
                        Text(
                            "AlcanzAR",
                            color = Color.White
                        )
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },

                    actions = {

                        IconButton(
                            onClick = onPerfilClick
                        ) {

                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },

                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor =
                                Color(0xFF1976D2)
                        )
                )
            }

        ) { padding ->

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(16.dp)

            ) {

                Text(
                    text = "Crear petición",
                    fontSize = 22.sp
                )

                Spacer(
                    Modifier.height(20.dp)
                )

                OutlinedTextField(
                    value = titulo,
                    onValueChange = {
                        titulo = it
                    },
                    label = {
                        Text("Título")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                    },
                    label = {
                        Text("Descripción")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = pais,
                    onValueChange = {
                        pais = it
                    },
                    label = {
                        Text("País")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = ciudad,
                    onValueChange = {
                        ciudad = it
                    },
                    label = {
                        Text("Ciudad")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = barrio,
                    onValueChange = {
                        barrio = it
                    },
                    label = {
                        Text("Barrio")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = fecha,
                    onValueChange = {
                        fecha = it
                    },
                    label = {
                        Text("Fecha")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                OutlinedTextField(
                    value = hora,
                    onValueChange = {
                        hora = it
                    },
                    label = {
                        Text("Hora")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    Modifier.height(32.dp)
                )

                Button(

                    onClick = {

                        val peticion =
                            Peticion(
                                titulo = titulo,
                                descripcion = descripcion,
                                pais = pais,
                                ciudad = ciudad,
                                barrio = barrio,
                                fecha = fecha,
                                hora = hora
                            )

                        onPublicarClick(
                            peticion
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth()

                ) {

                    Text(
                        "Publicar petición"
                    )
                }
            }
        }
    }
}