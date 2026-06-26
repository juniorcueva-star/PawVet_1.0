package com.example.pawvet_1.ui.screens.mascotas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.pawvet_1.ui.theme.PawVetBorder
import com.example.pawvet_1.ui.theme.PawVetPrimary
import com.example.pawvet_1.ui.theme.PawVetSurface
import com.example.pawvet_1.ui.theme.PawVetTextPrimary
import com.example.pawvet_1.ui.theme.PawVetTextSecondary
import com.example.pawvet_1.ui.viewmodel.MascotaViewModel

@Composable
fun MascotaDetalleScreen(
    mascotaId: Int,
    viewModel: MascotaViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 110.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(104.dp),
                    shape = CircleShape,
                    color = PawVetSurface,
                    border = BorderStroke(1.dp, PawVetBorder.copy(alpha = 0.8f)),
                    shadowElevation = 10.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = if (mascota.tipo.lowercase().contains("gato")) "🐱" else "🐶", fontSize = 44.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = mascota.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = PawVetTextPrimary
                )
                Text(
                    text = mascota.tipo,
                    style = MaterialTheme.typography.bodyLarge,
                    color = PawVetTextSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = PawVetSurface,
                    border = BorderStroke(1.dp, PawVetBorder.copy(alpha = 0.8f)),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        DetailRow(icon = Icons.Default.Star, label = "Raza", value = mascota.raza)
                        DetailRow(icon = Icons.Default.DateRange, label = "Edad", value = "${mascota.edad} años")
                        DetailRow(icon = Icons.Default.Info, label = "Peso", value = "${mascota.peso} kg")
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PawVetPrimary)
            }
        }
    }
}

@Composable
private fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(PawVetPrimary.copy(alpha = 0.10f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = PawVetPrimary)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = PawVetTextSecondary)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = PawVetTextPrimary)
        }
    }
}
