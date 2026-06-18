package com.example.pawvet_1.data.repository

import com.example.pawvet_1.data.dao.CitaDao
import com.example.pawvet_1.data.model.Cita
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para la gestión de datos de Citas.
 */
class CitaRepository(private val citaDao: CitaDao) {
    fun getAllCitas(): Flow<List<Cita>> = citaDao.getAllCitas()
    fun getCitasByMascota(mascotaId: Int): Flow<List<Cita>> = citaDao.getCitasByMascota(mascotaId)
    suspend fun getCitaById(id: Int): Cita? = citaDao.getCitaById(id)
    suspend fun insertCita(cita: Cita) = citaDao.insertCita(cita)
    suspend fun updateCita(cita: Cita) = citaDao.updateCita(cita)
    suspend fun deleteCita(cita: Cita) = citaDao.deleteCita(cita)
}
