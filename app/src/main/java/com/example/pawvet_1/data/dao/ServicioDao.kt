package com.example.pawvet_1.data.dao

import androidx.room.*
import com.example.pawvet_1.data.model.Servicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicios ORDER BY fecha DESC")
    fun getAllServicios(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios WHERE mascotaId = :mascotaId")
    fun getServiciosByMascota(mascotaId: Int): Flow<List<Servicio>>


    @Query("SELECT * FROM servicios WHERE id = :id")
    suspend fun getServicioById(id: Int): Servicio?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServicio(servicio: Servicio)

    @Update
    suspend fun updateServicio(servicio: Servicio)

    @Delete
    suspend fun deleteServicio(servicio: Servicio)
}
