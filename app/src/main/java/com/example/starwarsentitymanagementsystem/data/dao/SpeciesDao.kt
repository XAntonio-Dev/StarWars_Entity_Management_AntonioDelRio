package com.example.starwarsentitymanagementsystem.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.starwarsentitymanagementsystem.data.model.Species
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecie(species: Species)

    @Delete
    suspend fun deleteSpecie(species: Species)

    @Update
    suspend fun update(species: Species)

    @Query("SELECT * FROM species")
    fun getAllSpecies(): Flow<List<Species>>

    @Query("SELECT * FROM species ORDER BY name ASC")
    fun getAllSpeciesOrderedByName(): Flow<List<Species>>

    // Para regla de negocio: Evitar duplicados
    @Query("SELECT EXISTS (SELECT 1 FROM species WHERE name = :name COLLATE NOCASE)")
    suspend fun existsByName(name: String): Boolean
}