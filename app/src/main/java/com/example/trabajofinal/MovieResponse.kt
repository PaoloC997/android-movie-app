package com.example.trabajofinal

// MovieResponse.kt

// Respuesta del API de películas que contiene una lista de objetos Movie.

data class MovieResponse(
    val results: List<Movie>
)
