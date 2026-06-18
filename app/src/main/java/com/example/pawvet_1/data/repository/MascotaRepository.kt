package com.example.pawvet_1.data.repository

import com.example.pawvet_1.data.dao.MascotaDao
import com.example.pawvet_1.data.model.Mascota
import kotlinx.coroutines.flow.Flow

/**
 * REPOSITORY: Actúa como mediador entre el ViewModel y la Fuente de Datos (Room).
 * Su función es centralizar el acceso a los datos.
 */
class MascotaRepository(private val mascotaDao: MascotaDao) {
    
    // Obtiene el flujo de mascotas desde la DB local
    fun getAllMascotas(): Flow<List<Mascota>> = mascotaDao.getAllMascotas()

    // Busca una mascota por su ID (usado en Detalle y Formulario)
    suspend fun getMascotaById(id: Int): Mascota? = mascotaDao.getMascotaById(id)

    // Lógica para insertar
    suspend fun insertMascota(mascota: Mascota) = mascotaDao.insertMascota(mascota)

    // Lógica para actualizar
    suspend fun updateMascota(mascota: Mascota) = mascotaDao.updateMascota(mascota)

    // Lógica para eliminar
    suspend fun deleteMascota(mascota: Mascota) = mascotaDao.deleteMascota(mascota)
}
