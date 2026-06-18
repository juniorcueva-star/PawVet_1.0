package com.example.pawvet_1.ui.screens.mascotas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawvet_1.ui.components.PawVetBaseScreen
import com.example.pawvet_1.ui.viewmodel.MascotaViewModel

/**
 * PANTALLA DE DETALLE: Muestra la información completa de una mascota.
 * Recibe el ID de la mascota a través de la navegación.
 */
@Composable
fun MascotaDetalleScreen(
    mascotaId: Int,
    viewModel: MascotaViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Efecto de lanzamiento: Carga los datos de la mascota al entrar a la pantalla
    LaunchedEffect(mascotaId) {
        viewModel.seleccionarMascota(mascotaId)
    }

    PawVetBaseScreen(
        title = "Detalle de Mascota",
        onBack = onBack
    ) {
        val mascota = uiState.mascotaSeleccionada
        
        if (mascota != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cabecera con Nombre
                Text(
                    text = mascota.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = mascota.tipo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                // Tarjeta de Información Detallada
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoRow(icon = Icons.Default.Star, label = "Raza", value = mascota.raza)
                        InfoRow(icon = Icons.Default.DateRange, label = "Edad", value = "${mascota.edad} años")
                        InfoRow(icon = Icons.Default.Info, label = "Peso", value = "${mascota.peso} kg")
                    }
                }
            }
        } else {
            // Estado de carga si aún no se recupera la mascota de Room
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
    }
}
