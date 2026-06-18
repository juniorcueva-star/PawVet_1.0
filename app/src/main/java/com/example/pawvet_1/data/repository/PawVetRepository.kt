package com.example.pawvet_1.data.repository

import com.example.pawvet_1.data.dao.CitaDao
import com.example.pawvet_1.data.dao.MascotaDao
import com.example.pawvet_1.data.dao.ServicioDao
import com.example.pawvet_1.data.model.Cita
import com.example.pawvet_1.data.model.Mascota
import com.example.pawvet_1.data.model.Servicio
import kotlinx.coroutines.flow.Flow

class PawVetRepository(
    private val mascotaDao: MascotaDao,
    private val citaDao: CitaDao,
    private val servicioDao: ServicioDao
) {
    // Mascotas
    fun getAllMascotas(): Flow<List<Mascota>> = mascotaDao.getAllMascotas()
    suspend fun insertMascota(mascota: Mascota) = mascotaDao.insertMascota(mascota)
    suspend fun deleteMascota(mascota: Mascota) = mascotaDao.deleteMascota(mascota)

    // Citas
    fun getAllCitas(): Flow<List<Cita>> = citaDao.getAllCitas()
    suspend fun insertCita(cita: Cita) = citaDao.insertCita(cita)

    // Servicios
    fun getAllServicios(): Flow<List<Servicio>> = servicioDao.getAllServicios()
    suspend fun insertServicio(servicio: Servicio) = servicioDao.insertServicio(servicio)
}
