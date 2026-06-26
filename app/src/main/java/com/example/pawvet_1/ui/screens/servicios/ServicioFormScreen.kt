package com.example.pawvet_1.ui.screens.servicios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawvet_1.ui.components.PawVetBaseScreen
import com.example.pawvet_1.ui.screens.citas.PremiumFormSection
import com.example.pawvet_1.ui.theme.BlobBlue
import com.example.pawvet_1.ui.theme.BlobCoral
import com.example.pawvet_1.ui.theme.BlobGreen
import com.example.pawvet_1.ui.theme.BlobYellow
import com.example.pawvet_1.ui.theme.PawVetPrimary
import com.example.pawvet_1.ui.theme.PawVetTextPrimary
import com.example.pawvet_1.ui.viewmodel.MascotaViewModel
import com.example.pawvet_1.ui.viewmodel.ServicioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioFormScreen(
    viewModel: ServicioViewModel,
    mascotaViewModel: MascotaViewModel,
    onBack: () -> Unit
) {
    val mascotaState by mascotaViewModel.uiState.collectAsState()

    var selectedMascotaId by remember { mutableStateOf<Int?>(null) }
    var selectedTipo by remember { mutableStateOf("Baño y Corte") }
    var selectedFechaMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    var showDatePicker by remember { mutableStateOf(false) }
    var expandedMascotas by remember { mutableStateOf(false) }
    var expandedTipos by remember { mutableStateOf(false) }

    val tiposServicio = listOf("Baño y Corte", "Limpieza Dental", "Corte de Uñas", "Baño Medicado", "Spa Completo")
    val dateFormatter = SimpleDateFormat("EEEE, dd MMMM", Locale("es", "ES"))

    PawVetBaseScreen(
        title = "Reserva Estética",
        onBack = onBack
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            PremiumFormSection(title = "Paciente", icon = Icons.Default.Favorite, iconBg = BlobBlue) {
                ExposedDropdownMenuBox(
                    expanded = expandedMascotas,
                    onExpandedChange = { expandedMascotas = !expandedMascotas }
                ) {
                    val mascota = mascotaState.listaMascotas.find { it.id == selectedMascotaId }
                    OutlinedTextField(
                        value = mascota?.nombre ?: "Selecciona tu mascota",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedMascotas) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PawVetPrimary,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                        )
                    )
                    ExposedDropdownMenu(expanded = expandedMascotas, onDismissRequest = { expandedMascotas = false }) {
                        mascotaState.listaMascotas.forEach { mascota ->
                            DropdownMenuItem(
                                text = { Text("${mascota.nombre} (${mascota.raza})") },
                                onClick = {
                                    selectedMascotaId = mascota.id
                                    expandedMascotas = false
                                }
                            )
                        }
                    }
                }
            }

            PremiumFormSection(title = "Servicio", icon = Icons.Default.Star, iconBg = BlobGreen) {
                ExposedDropdownMenuBox(
                    expanded = expandedTipos,
                    onExpandedChange = { expandedTipos = !expandedTipos }
                ) {
                    OutlinedTextField(
                        value = selectedTipo,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedTipos) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PawVetPrimary,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                        )
                    )
                    ExposedDropdownMenu(expanded = expandedTipos, onDismissRequest = { expandedTipos = false }) {
                        tiposServicio.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(tipo) },
                                onClick = {
                                    selectedTipo = tipo
                                    expandedTipos = false
                                }
                            )
                        }
                    }
                }
            }

            PremiumFormSection(title = "Fecha sugerida", icon = Icons.Default.DateRange, iconBg = BlobYellow) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dateFormatter.format(Date(selectedFechaMillis)).replaceFirstChar { it.uppercase() },
                            color = PawVetTextPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Cambiar",
                            color = PawVetPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            PremiumFormSection(title = "Disponibilidad", icon = Icons.Default.Check, iconBg = BlobCoral) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("Express", "Estándar", "Premium").forEach { slot ->
                        FilterChip(
                            selected = selectedTipo.contains(slot, ignoreCase = true),
                            onClick = { },
                            label = { Text(slot) },
                            shape = RoundedCornerShape(12.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.White,
                                selectedContainerColor = PawVetPrimary.copy(alpha = 0.12f),
                                selectedLabelColor = PawVetPrimary
                            )
                        )
                    }
                }
            }

            Button(
                onClick = {
                    selectedMascotaId?.let { idMascota ->
                        viewModel.guardarServicio(
                            mascotaId = idMascota,
                            tipo = selectedTipo,
                            fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedFechaMillis))
                        )
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = selectedMascotaId != null && selectedTipo.isNotBlank(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PawVetPrimary)
            ) {
                Text("Confirmar cita estética", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedFechaMillis)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedFechaMillis = datePickerState.selectedDateMillis ?: selectedFechaMillis
                    showDatePicker = false
                }) { Text("Aceptar", color = PawVetPrimary, fontWeight = FontWeight.Bold) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
