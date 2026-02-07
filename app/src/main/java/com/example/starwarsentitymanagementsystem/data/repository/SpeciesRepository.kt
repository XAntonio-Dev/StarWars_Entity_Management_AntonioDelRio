package com.example.starwarsentitymanagementsystem.data.repository

import com.example.starwarsentitymanagementsystem.data.dao.SpeciesDao
import com.example.starwarsentitymanagementsystem.data.model.Species
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesRepository @Inject constructor(
    private val speciesDao: SpeciesDao
) {
    fun getAllSpecies(): Flow<List<Species>> = speciesDao.getAllSpecies()

    fun getAllSpeciesOrdered(): Flow<List<Species>> = speciesDao.getAllSpeciesOrderedByName()

    suspend fun addSpecies(species: Species) = speciesDao.insertSpecie(species)

    suspend fun updateSpecies(species: Species) = speciesDao.update(species)

    suspend fun deleteSpecies(species: Species) = speciesDao.deleteSpecie(species)

    suspend fun existsByName(name: String): Boolean = speciesDao.existsByName(name)
}