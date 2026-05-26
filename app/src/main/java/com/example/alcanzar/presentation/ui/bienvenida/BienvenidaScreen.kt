package com.example.alcanzar.presentation.ui.bienvenida

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BienvenidaScreen(
    onPerfilClick: () -> Unit,
    onAcercaClick: () -> Unit,
    onPeticionesClick: () -> Unit
) {
    val drawerState =
        rememberDrawerState(
            DrawerValue.Closed
        )

    val scope =
        rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {

                DrawerItem("Inicio") {
                    scope.launch {
                        drawerState.close()
                    }
                }

                HorizontalDivider()

                DrawerItem("Realizar una petición") {
                    scope.launch {
                        drawerState.close()
                    }
                }

                DrawerItem("Buscar peticiones") {
                    scope.launch {
                        drawerState.close()
                        onPeticionesClick()
                    }
                }

                HorizontalDivider()

                DrawerItem("Publicar un viaje") {
                    scope.launch {
                        drawerState.close()
                    }
                }

                DrawerItem("Viajes cercanos") {
                    scope.launch {
                        drawerState.close()
                    }
                }

                HorizontalDivider()

                DrawerItem("Mis chats") {
                    scope.launch {
                        drawerState.close()
                    }
                }

                HorizontalDivider()

                DrawerItem("Notificaciones") {
                    scope.launch {
                        drawerState.close()
                    }
                }

                HorizontalDivider()

                DrawerItem("Acerca de") {
                    scope.launch {
                        drawerState.close()
                        onAcercaClick()
                    }
                }
            }
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
                                null,
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
                                null,
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = "Bienvenido a AlcanzAR",
                    style =
                        MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    Modifier.height(16.dp)
                )

                Text(
                    text =
                        "Una aplicación diseñada para la comunidad"
                )
            }
        }
    }
}

@Composable
fun DrawerItem(
    text: String,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(text)
        },
        selected = false,
        onClick = onClick,
        modifier =
            Modifier.padding(
                horizontal = 12.dp,
                vertical = 4.dp
            )
    )
}