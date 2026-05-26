package com.example.alcanzar.presentation.ui.peticiones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcanzar.domain.model.Peticion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeticionesScreen(
    peticiones: List<Peticion>,
    onPerfilClick: () -> Unit,
    onInicioClick: () -> Unit,
    onAcercaClick: () -> Unit
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
                        onInicioClick()
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
            ) {

                Text(
                    text = "Peticiones cercanas",
                    fontSize = 22.sp,
                    modifier =
                        Modifier.padding(
                            start = 18.dp,
                            top = 20.dp,
                            bottom = 12.dp
                        )
                )

                LazyColumn(
                    modifier =
                        Modifier.fillMaxSize(),
                    contentPadding =
                        PaddingValues(14.dp),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(peticiones) {
                        PeticionCard(it)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(
    texto: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}