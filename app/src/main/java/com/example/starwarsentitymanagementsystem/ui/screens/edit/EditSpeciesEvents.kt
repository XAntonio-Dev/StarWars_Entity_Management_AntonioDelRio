package com.example.starwarsentitymanagementsystem.ui.screens.edit

data class EditSpeciesEvents(
    val onNameChange: (String) -> Unit = {},
    val onClassificationChange: (String) -> Unit = {},
    val onDesignationChange: (String) -> Unit = {},
    val onLanguageChange: (String) -> Unit = {},
    val onDiscoveryDateChange: (String) -> Unit = {},
    val onIsArtificialChange: (Boolean) -> Unit = {},
    val onUpdate: () -> Unit = {},
    val onDelete: () -> Unit = {},
    val onCancel: () -> Unit = {}
)