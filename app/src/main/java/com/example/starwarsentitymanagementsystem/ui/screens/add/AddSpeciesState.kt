package com.example.starwarsentitymanagementsystem.ui.screens.add

data class AddSpeciesState(
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
    val isArtificial: Boolean = false,
    val showDuplicateError: Boolean = false
)