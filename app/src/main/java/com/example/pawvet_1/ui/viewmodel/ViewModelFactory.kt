package com.example.pawvet_1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pawvet_1.data.repository.BreedsRepository
import com.example.pawvet_1.data.repository.CitaRepository
import com.example.pawvet_1.data.repository.MascotaRepository
import com.example.pawvet_1.data.repository.ServicioRepository

/**
 * Fábrica para instanciar ViewModels con sus respectivos repositorios.
 */
class ViewModelFactory(private val repository: Any) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MascotaViewModel::class.java) -> {
                MascotaViewModel(repository as MascotaRepository) as T
            }
            modelClass.isAssignableFrom(BreedsViewModel::class.java) -> {
                BreedsViewModel(repository as BreedsRepository) as T
            }
            modelClass.isAssignableFrom(CitaViewModel::class.java) -> {
                CitaViewModel(repository as CitaRepository) as T
            }
            modelClass.isAssignableFrom(ServicioViewModel::class.java) -> {
                ServicioViewModel(repository as ServicioRepository) as T
            }
            else -> throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
        }
    }
}
