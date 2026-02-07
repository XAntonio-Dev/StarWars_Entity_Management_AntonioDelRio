package com.example.starwarsentitymanagementsystem.ui.screens.edit

data class EditSpeciesState(
    val id: Int = 0,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val name: String = "",
    val nameError: String? = null,
    val classification: String = "",
    val classificationError: String? = null,
    val designation: String = "",
    val language: String = "",
    val languageError: String? = null,
    val discoveryDate: String = "",
    val discoveryDateError: String? = null,
    val description: String = "",
    val isArtificial: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isOperationSuccess: Boolean = false
)