package com.example.pawvet_1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Citas : Screen("citas")
    object Perfil : Screen("perfil")

    object MascotaDetalle : Screen("mascota_detalle/{mascotaId}") {
        fun createRoute(id: Int) = "mascota_detalle/$id"
    }

    object MascotaForm : Screen("mascota_form/{mascotaId}") {
        fun createRoute(id: Int = 0) = "mascota_form/$id"
    }

    object CitaForm : Screen("cita_form/{citaId}") {
        fun createRoute(id: Int = 0) = "cita_form/$id"
    }

    object Servicios : Screen("servicios")

    object ServicioForm : Screen("servicio_form/{servicioId}") {
        fun createRoute(id: Int = 0) = "servicio_form/$id"
    }

    object ConsultasRapidas : Screen("consultas_rapidas")
}
