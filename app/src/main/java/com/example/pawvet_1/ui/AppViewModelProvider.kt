package com.example.pawvet_1.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pawvet_1.PawVetApplication
import com.example.pawvet_1.ui.viewmodel.BreedsViewModel
import com.example.pawvet_1.ui.viewmodel.CitaViewModel
import com.example.pawvet_1.ui.viewmodel.MascotaViewModel
import com.example.pawvet_1.ui.viewmodel.ServicioViewModel

/**
 * Proveedor para crear instancias de ViewModels en toda la aplicación.
 * Permite inyectar los repositorios específicos en cada ViewModel de forma limpia.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Inicializador para MascotaViewModel (Room)
        initializer {
            MascotaViewModel(
                pawVetApplication().container.mascotaRepository
            )
        }

        // Inicializador para BreedsViewModel (Retrofit)
        initializer {
            BreedsViewModel(
                pawVetApplication().container.breedsRepository
            )
        }

        // Inicializador para CitaViewModel
        initializer {
            CitaViewModel(
                pawVetApplication().container.citaRepository
            )
        }

        // Inicializador para ServicioViewModel
        initializer {
            ServicioViewModel(
                pawVetApplication().container.servicioRepository
            )
        }
    }
}

/**
 * Función de extensión para obtener la instancia de PawVetApplication.
 */
fun CreationExtras.pawVetApplication(): PawVetApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PawVetApplication)
