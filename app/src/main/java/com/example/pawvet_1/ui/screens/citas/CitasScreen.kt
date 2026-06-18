package com.example.pawvet_1.ui.screens.citas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawvet_1.data.model.Cita
import com.example.pawvet_1.ui.components.PawVetBaseScreen
import com.example.pawvet_1.ui.viewmodel.CitaViewModel

/**
 * VISTA (VIEW): Representa la interfaz de usuario de Citas.
 * No contiene lógica de negocio; solo observa el UiState del ViewModel
 * y reacciona a los cambios (Recomposición).
 */
@Composable
fun CitasScreen(
    viewModel: CitaViewModel,
    onNavigateToForm: (Int) -> Unit,
    onBack: () -> Unit
) {
    // Observamos el flujo de datos del ViewModel y lo convertimos en un estado de Compose
    val uiState by viewModel.uiState.collectAsState()

    PawVetBaseScreen(
        title = "Gestión de Citas",
        onBack = onBack,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToForm(0) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agendar Cita")
            }
        }
    ) {
        // Lógica de visualización basada en el estado
        if (uiState.listaCitas.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📅", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No hay citas registradas.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        "¡Agenda una para tu mascota!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            // LazyColumn es un componente eficiente para listas grandes
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.listaCitas) { cita ->
                    CitaCard(
                        cita = cita,
                        onClick = { onNavigateToForm(cita.id) },
                        onDelete = { viewModel.eliminarCita(cita) }
                    )
                }
            }
        }
    }
}

@Composable
fun CitaCard(cita: Cita, onClick: () -> Unit, onDelete: () -> Unit) {
    // Personalización visual según el tipo de cita
    val (emoji, containerColor) = when (cita.tipo.lowercase()) {
        "vacunación", "vacuna" -> "💉" to MaterialTheme.colorScheme.tertiaryContainer
        "emergencia" -> "🚨" to MaterialTheme.colorScheme.errorContainer
        "control" -> "📋" to MaterialTheme.colorScheme.secondaryContainer
        "consulta" -> "🩺" to MaterialTheme.colorScheme.primaryContainer
        else -> "🗓️" to MaterialTheme.colorScheme.surfaceVariant
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(16.dp),
                color = containerColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = emoji, fontSize = 28.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cita.tipo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📅 ${cita.fecha}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "🕒 ${cita.hora}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
