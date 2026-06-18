package com.example.pawvet_1.ui.viewmodel

import com.example.pawvet_1.data.model.Mascota

data class MascotaUiState(
    val listaMascotas: List<Mascota> = emptyList(),
    val mascotaSeleccionada: Mascota? = null,
    val isLoading: Boolean = false,
    val mensajeError: String? = null
)
