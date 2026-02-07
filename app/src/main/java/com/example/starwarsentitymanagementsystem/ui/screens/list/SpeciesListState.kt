package com.example.starwarsentitymanagementsystem.ui.screens.list

import com.example.starwarsentitymanagementsystem.data.model.Species

sealed class SpeciesListState {
    data object Loading : SpeciesListState()
    data object NoData : SpeciesListState()
    data class Success(val dataset: List<Species>) : SpeciesListState()
}