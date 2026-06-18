package com.example.pawvet_1.ui.viewmodel

import com.example.pawvet_1.data.model.Servicio

/**
 * Estado de la interfaz de usuario para la pantalla de Servicios.
 */
data class ServicioUiState(
    val listaServicios: List<Servicio> = emptyList(),
    val servicioSeleccionado: Servicio? = null,
    val isLoading: Boolean = false,
    val mensajeError: String? = null
)
