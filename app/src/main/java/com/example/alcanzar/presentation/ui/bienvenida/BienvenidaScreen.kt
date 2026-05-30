package com.example.alcanzar.presentation.ui.bienvenida

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import com.example.alcanzar.presentation.ui.components.AppDrawer
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
    onPeticionesClick: () -> Unit,
    onViajesClick: () -> Unit,
    onCrearViajeClick: () -> Unit,
    onCrearPeticionClick: () -> Unit
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

            AppDrawer(

                onInicioClick = {
                    scope.launch {
                        drawerState.close()
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