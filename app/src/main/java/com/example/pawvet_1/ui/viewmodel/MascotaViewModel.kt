package com.example.pawvet_1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawvet_1.data.model.Mascota
import com.example.pawvet_1.data.repository.MascotaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * VIEWMODEL: El "cerebro" de la pantalla de mascotas.
 * Se encarga de transformar los datos del repositorio en estados que la UI pueda entender.
 * Sigue el patrón MVVM separando la lógica de la interfaz.
 */
class MascotaViewModel(private val repository: MascotaRepository) : ViewModel() {

    // Estado interno (Mutable) - Solo el ViewModel puede modificarlo
    private val _uiState = MutableStateFlow(MascotaUiState())
    
    // Estado público (Solo lectura) - La Vista observa este estado
    val uiState: StateFlow<MascotaUiState> = _uiState.asStateFlow()

    init {
        // Al iniciar, cargamos la lista de mascotas
        listarMascotas()
    }

    private fun listarMascotas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Observamos el Flow de la base de datos (actualización en tiempo real)
            repository.getAllMascotas().collect { lista ->
                _uiState.update { it.copy(listaMascotas = lista, isLoading = false) }
            }
        }
    }

    fun seleccionarMascota(id: Int) {
        viewModelScope.launch {
            val mascota = repository.getMascotaById(id)
            _uiState.update { it.copy(mascotaSeleccionada = mascota) }
        }
    }

    // Reseteamos el estado para evitar mostrar datos previos al crear una nueva mascota
    fun resetSeleccion() {
        _uiState.update { it.copy(mascotaSeleccionada = null) }
    }

    /**
     * Función para Guardar o Actualizar.
     * La Vista llama a esta función cuando el usuario presiona el botón.
     */
    fun guardarMascota(id: Int = 0, nombre: String, tipo: String, raza: String, edad: Int, peso: Double) {
        viewModelScope.launch {
            val mascota = Mascota(id = id, nombre = nombre, tipo = tipo, raza = raza, edad = edad, peso = peso)
            if (id == 0) {
                repository.insertMascota(mascota)
            } else {
                repository.updateMascota(mascota)
            }
        }
    }

    fun eliminarMascota(mascota: Mascota) {
        viewModelScope.launch {
            repository.deleteMascota(mascota)
        }
    }
}
