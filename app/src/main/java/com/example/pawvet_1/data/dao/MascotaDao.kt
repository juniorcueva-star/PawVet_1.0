package com.example.pawvet_1.data.dao

import androidx.room.*
import com.example.pawvet_1.data.model.Mascota
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object): Define las consultas SQL para la tabla 'mascotas'.
 * Room genera automáticamente la implementación de estas funciones.
 */
@Dao
interface MascotaDao {
    // Retorna un Flow: la UI se actualizará automáticamente si los datos cambian.
    @Query("SELECT * FROM mascotas ORDER BY nombre ASC")
    fun getAllMascotas(): Flow<List<Mascota>>

    @Query("SELECT * FROM mascotas WHERE id = :id")
    suspend fun getMascotaById(id: Int): Mascota?

    // OnConflictStrategy.REPLACE: Si el ID ya existe, reemplaza los datos (útil para edición).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMascota(mascota: Mascota)

    @Update
    suspend fun updateMascota(mascota: Mascota)

    @Delete
    suspend fun deleteMascota(mascota: Mascota)
}
