@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.trabajofinal

// MovieDetailActivity.kt

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {

    lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Obtener referencias a las vistas del diseño
        poster = findViewById(R.id.movie_poster)
        val titleTextView = findViewById<TextView>(R.id.movie_title)
        val ratingTextView = findViewById<TextView>(R.id.movie_rating)
        val releaseTextView = findViewById<TextView>(R.id.movie_release_date)
        val overviewTextView = findViewById<TextView>(R.id.movie_overview)

        // Obtener el objeto Movie enviado desde MainActivity a través del Intent
        val selectedMovie = intent.getParcelableExtra<Movie>("movie")

        // Comprobar si se ha recibido correctamente el objeto Movie
        selectedMovie?.let {
            // Establecer el título, descripción, calificación y fecha de estreno de la película en sus vistas correspondientes
            titleTextView.text = it.title
            overviewTextView.text = it.overview
            ratingTextView.text = it.rating.toString()
            releaseTextView.text = it.releaseDate

            // Cargar el póster de la película utilizando Glide
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/${it.posterPath}")
                .into(poster)
        }
    }
}



