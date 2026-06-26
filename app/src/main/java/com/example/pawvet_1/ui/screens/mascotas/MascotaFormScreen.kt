package com.example.pawvet_1.ui.screens.mascotas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawvet_1.ui.components.PawVetBaseScreen
import com.example.pawvet_1.ui.screens.citas.PremiumFormSection
import com.example.pawvet_1.ui.theme.BlobBlue
import com.example.pawvet_1.ui.theme.BlobCoral
import com.example.pawvet_1.ui.theme.BlobGreen
import com.example.pawvet_1.ui.theme.BlobYellow
import com.example.pawvet_1.ui.theme.PawVetPrimary
import com.example.pawvet_1.ui.viewmodel.MascotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MascotaFormScreen(
    mascotaId: Int,
    viewModel: MascotaViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Perro") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }

    val tiposMascota = listOf("Perro", "Gato", "Hamster", "Conejo", "Ave", "Otro")
    val razasComunes = listOf("Labrador", "Pastor Alemán", "Persa", "Siamés", "Pug", "Chihuahua", "Bulldog", "Mestizo", "Otro")

    var expandedTipo by remember { mutableStateOf(false) }
    var expandedRaza by remember { mutableStateOf(false) }

    LaunchedEffect(mascotaId) {
        if (mascotaId > 0) viewModel.seleccionarMascota(mascotaId) else viewModel.resetSeleccion()
    }

    LaunchedEffect(uiState.mascotaSeleccionada) {
        uiState.mascotaSeleccionada?.let {
            nombre = it.nombre
            tipo = it.tipo
            raza = it.raza
            edad = it.edad.toString()
            peso = it.peso.toString()
        }
    }

    PawVetBaseScreen(
        title = if (mascotaId > 0) "Editar Mascota" else "Nueva Mascota",
        onBack = onBack
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            PremiumFormSection(title = "Información básica", icon = Icons.Default.Face, iconBg = BlobBlue) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    PremiumInput(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = "Nombre de la mascota"
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedTipo,
                        onExpandedChange = { expandedTipo = !expandedTipo }
                    ) {
                        OutlinedTextField(
                            value = tipo,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PawVetPrimary,
                                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                            )
                        )
                        ExposedDropdownMenu(expanded = expandedTipo, onDismissRequest = { expandedTipo = false }) {
                            tiposMascota.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        tipo = item
                                        expandedTipo = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            PremiumFormSection(title = "Perfil de raza", icon = Icons.Default.Star, iconBg = BlobGreen) {
                ExposedDropdownMenuBox(
                    expanded = expandedRaza,
                    onExpandedChange = { expandedRaza = !expandedRaza }
                ) {
                    OutlinedTextField(
                        value = raza,
                        onValueChange = { raza = it },
                        label = { Text("Raza") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRaza) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PawVetPrimary,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                        )
                    )
                    ExposedDropdownMenu(expanded = expandedRaza, onDismissRequest = { expandedRaza = false }) {
                        razasComunes.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    raza = item
                                    expandedRaza = false
                                }
                            )
                        }
                    }
                }
            }

            PremiumFormSection(title = "Datos clínicos", icon = Icons.Default.Info, iconBg = BlobYellow) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PremiumInput(
                        value = edad,
                        onValueChange = { if (it.all(Char::isDigit)) edad = it },
                        label = "Edad",
                        modifier = Modifier.weight(1f),
                        keyboardType = KeyboardType.Number
                    )
                    PremiumInput(
                        value = peso,
                        onValueChange = { peso = it },
                        label = "Peso",
                        modifier = Modifier.weight(1f),
                        keyboardType = KeyboardType.Decimal
                    )
                }
            }

            PremiumFormSection(title = "Resumen", icon = Icons.Default.DateRange, iconBg = BlobCoral) {
                Text(
                    text = "Completa estos datos para personalizar mejor citas, cuidados y recomendaciones.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {
                    viewModel.guardarMascota(
                        id = mascotaId,
                        nombre = nombre,
                        tipo = tipo,
                        raza = raza,
                        edad = edad.toIntOrNull() ?: 0,
                        peso = peso.toDoubleOrNull() ?: 0.0
                    )
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(18.dp),
                enabled = nombre.isNotBlank() && raza.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PawVetPrimary)
            ) {
                Text(
                    text = if (mascotaId > 0) "Guardar cambios" else "Registrar mascota",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun PremiumInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PawVetPrimary,
            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
        )
    )
}
