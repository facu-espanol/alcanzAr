package com.example.pruebaarquitecturalimpia.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.pruebaarquitecturalimpia.domain.model.Tarea

@Composable
fun TareasScreen(
    tareas: List<Tarea>,
    onAgregarTarea: (String) -> Unit,
    onAlternarEstado: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var textoInput by remember { mutableStateOf("") }

    // Se asigna el parámetro 'modifier' (minúscula) al contenedor raíz
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = textoInput,
                onValueChange = { textoInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("Nueva tarea") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onAgregarTarea(textoInput)
                    textoInput = ""
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Añadir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tareas) { tarea ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAlternarEstado(tarea.id) }
                        .padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = tarea.estaCompletada,
                        onCheckedChange = { onAlternarEstado(tarea.id) }
                    )
                    Text(
                        text = tarea.titulo,
                        modifier = Modifier.padding(start = 8.dp, top = 12.dp),
                        textDecoration = if (tarea.estaCompletada) TextDecoration.LineThrough else null
                    )
                }
            }
        }
    }
}