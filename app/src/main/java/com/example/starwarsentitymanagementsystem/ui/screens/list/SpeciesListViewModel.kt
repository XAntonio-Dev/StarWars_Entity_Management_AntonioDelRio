package com.example.starwarsentitymanagementsystem.ui.screens.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsentitymanagementsystem.data.model.Species
import com.example.starwarsentitymanagementsystem.data.repository.SpeciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeciesListViewModel @Inject constructor(
    private val repository: SpeciesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SpeciesListState>(SpeciesListState.Loading)
    val uiState: StateFlow<SpeciesListState> = _uiState.asStateFlow()

    // --- ESTADO COMPARTIDO (Para Editar y Borrar) ---
    var speciesToEdit by mutableStateOf<Species?>(null)
        private set

    var speciesToDelete by mutableStateOf<Species?>(null)
        private set

    private var isSortedAscending = false

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _uiState.value = SpeciesListState.Loading
            delay(500)

            val flow = if (isSortedAscending) {
                repository.getAllSpeciesOrdered()
            } else {
                repository.getAllSpecies()
            }

            flow.collect { list ->
                if (list.isEmpty()) {
                    _uiState.value = SpeciesListState.NoData
                } else {
                    _uiState.value = SpeciesListState.Success(list)
                }
            }
        }
    }

    fun onSortByName() {
        isSortedAscending = !isSortedAscending
        getData()
    }

    // --- LÓGICA DE BORRADO (Diálogo) ---

    // 1. Usuario pide borrar (Long Click)
    fun onRequestDelete(species: Species) {
        speciesToDelete = species
    }

    // 2. Usuario cancela
    fun onDismissDeleteDialog() {
        speciesToDelete = null
    }

    // 3. Usuario confirma -> Borramos en BD
    fun onConfirmDelete() {
        speciesToDelete?.let { species ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteSpecies(species)
            }
        }
        speciesToDelete = null
    }

    // --- LÓGICA DE EDICIÓN ---
    fun onEditAccount(species: Species) {
        speciesToEdit = species
    }
}