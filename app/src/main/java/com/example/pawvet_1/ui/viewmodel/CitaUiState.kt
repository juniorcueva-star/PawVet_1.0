package com.example.pawvet_1.ui.viewmodel

import com.example.pawvet_1.data.model.Cita

/**
 * Estado de la interfaz de usuario para la pantalla de Citas.
 */
data class CitaUiState(
    val listaCitas: List<Cita> = emptyList(),
    val citaSeleccionada: Cita? = null,
    val isLoading: Boolean = false,
    val mensajeError: String? = null
)
