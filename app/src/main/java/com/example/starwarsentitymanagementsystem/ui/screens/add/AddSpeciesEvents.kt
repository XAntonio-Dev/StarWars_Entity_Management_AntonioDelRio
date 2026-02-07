package com.example.starwarsentitymanagementsystem.ui.screens.add

data class AddSpeciesEvents(
    val onNameChange: (String) -> Unit,
    val onClassificationChange: (String) -> Unit,
    val onDesignationChange: (String) -> Unit,
    val onLanguageChange: (String) -> Unit,
    val onDiscoveryDateChange: (String) -> Unit,
    val onIsArtificialChange: (Boolean) -> Unit,
    val onCreate: () -> Unit,
    val onCancel: () -> Unit
)