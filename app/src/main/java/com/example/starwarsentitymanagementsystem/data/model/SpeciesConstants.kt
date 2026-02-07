package com.example.starwarsentitymanagementsystem.data.model

/**
 * Objeto para guardar constantes estáticas y evitar duplicidad.
 * Así si queremos añadir una clasificación nueva, solo tocamos aquí.
 */
object SpeciesConstants {
    val CLASSIFICATIONS = listOf(
        "Mamífero",
        "Reptil",
        "Anfibio",
        "Insectoide",
        "Gastrópodo",
        "Ave",
        "Droide",
        "Desconocido"
    )
}