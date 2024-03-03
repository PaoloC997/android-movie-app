package com.example.trabajofinal// com.example.trabajofinal.MainActivity.kt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView // declarar la variable que contendrá la RecyclerView
    private lateinit var movieAdapter: MovieAdapter // declarar el adaptador que se usará en la RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // inflar el layout de la actividad

        recyclerView = findViewById(R.id.recycler_view) // buscar la RecyclerView en el layout
        recyclerView.layoutManager = LinearLayoutManager(this) // asignar un LayoutManager a la RecyclerView
        movieAdapter = MovieAdapter(this, listOf()) // crear una instancia de MovieAdapter
        recyclerView.adapter = movieAdapter // asignar el adaptador a la RecyclerView

        // hacer una solicitud a la API para obtener los datos de las películas populares
        val apiKey = "yourapikey"
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val movies = mutableListOf<Movie>()
                val results = response.getJSONArray("results")
                for (i in 0 until results.length()) {
                    val movie = results.getJSONObject(i)
                    val movieId = movie.getInt("id")
                    val rating = movie.getInt("vote_average")
                    val title = movie.getString("title")
                    val posterPath = movie.getString("poster_path")
                    val movieOverview = movie.getString("overview")
                    val releaseDate = movie.getString("release_date")
                    movies.add(Movie(movieId, rating, title, posterPath, movieOverview, releaseDate))
                }
                movieAdapter.movies = movies // asignar la lista de películas obtenidas al adaptador
            },
            { error ->
                Log.e("MainActivity", "Error fetching movies: $error")
            })

        Volley.newRequestQueue(this).add(request) // agregar la solicitud a la cola de solicitudes de Volley
    }

    inner class MovieAdapter(private val context: Context, var movies: List<Movie>) :
        RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false) // inflar el layout de los elementos de la lista
            return ViewHolder(view) // retornar una instancia de ViewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val movie = movies[position] // obtener la película en la posición especificada
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${movie.posterPath}") // cargar la imagen de la película usando Glide
                .into(holder.poster) // mostrar la imagen en el ImageView correspondiente
            holder.title.text = movie.title // asignar el título de la película al TextView correspondiente
            holder.itemView.setOnClickListener { // agregar un listener al itemView de la película
                val selectedMovie = movieAdapter.movies[position] // obtener la película seleccionada
                val intent = Intent(context, MovieDetailActivity::class.java) // crear una instancia de Intent para mostrar los detalles de la película
                intent.putExtra("movie", selectedMovie) // agregar la información de la película al Intent
                intent.putExtra("poster", selectedMovie.posterPath) // agregar la ruta de la imagen al Intent
                startActivity(intent) // iniciar la actividad MovieDetailActivity
            }
        }

        override fun getItemCount(): Int {
            return movies.size // retornar la cantidad de películas en la lista
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Se declaran las propiedades que referencian los elementos de la vista
            val poster: ImageView = itemView.findViewById(R.id.movie_poster)
            val title: TextView = itemView.findViewById(R.id.movie_title)
        }
    }
}


