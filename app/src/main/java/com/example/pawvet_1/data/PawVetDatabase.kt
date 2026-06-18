package com.example.pawvet_1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pawvet_1.data.dao.CitaDao
import com.example.pawvet_1.data.dao.MascotaDao
import com.example.pawvet_1.data.dao.ServicioDao
import com.example.pawvet_1.data.model.Cita
import com.example.pawvet_1.data.model.Mascota
import com.example.pawvet_1.data.model.Servicio

@Database(
    entities = [Mascota::class, Cita::class, Servicio::class], 
    version = 4, // Subimos a 4 para forzar la limpieza total y resolver el error de integridad
    exportSchema = false
)
abstract class PawVetDatabase : RoomDatabase() {

    abstract fun mascotaDao(): MascotaDao
    abstract fun citaDao(): CitaDao
    abstract fun servicioDao(): ServicioDao

    companion object {
        @Volatile
        private var Instance: PawVetDatabase? = null

        fun getDatabase(context: Context): PawVetDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PawVetDatabase::class.java, "pawvet_db")
                    .fallbackToDestructiveMigration() // Esto limpia la DB vieja si hay cambios, evitando el crash
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
