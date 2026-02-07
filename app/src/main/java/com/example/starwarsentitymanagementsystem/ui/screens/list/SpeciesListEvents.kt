package com.example.starwarsentitymanagementsystem.ui.screens.list

import com.example.starwarsentitymanagementsystem.data.model.Species

data class SpeciesListEvents(
    val onAdd: () -> Unit,
    val onEdit: (Species) -> Unit,
    val onSort: () -> Unit,
    val onDelete: (Species) -> Unit,
)