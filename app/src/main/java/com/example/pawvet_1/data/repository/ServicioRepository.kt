package com.example.pawvet_1.data.repository

import com.example.pawvet_1.data.dao.ServicioDao
import com.example.pawvet_1.data.model.Servicio
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para la gestión de datos de Servicios.
 */
class ServicioRepository(private val servicioDao: ServicioDao) {
    fun getAllServicios(): Flow<List<Servicio>> = servicioDao.getAllServicios()
    fun getServiciosByMascota(mascotaId: Int): Flow<List<Servicio>> = servicioDao.getServiciosByMascota(mascotaId)
    suspend fun getServicioById(id: Int): Servicio? = servicioDao.getServicioById(id)
    suspend fun insertServicio(servicio: Servicio) = servicioDao.insertServicio(servicio)
    suspend fun updateServicio(servicio: Servicio) = servicioDao.updateServicio(servicio)
    suspend fun deleteServicio(servicio: Servicio) = servicioDao.deleteServicio(servicio)
}
