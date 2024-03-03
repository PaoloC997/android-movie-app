// MovieApi.kt

import com.example.trabajofinal.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Se define una interfaz llamada MovieApi. Esta interfaz define un método que usa la anotación @GET de
// Retrofit para definir la ruta de la API y @Query para especificar un parámetro de consulta llamado
// api_key con valor String para obtener la lista de películas populares de The Movie DB API.
// El método devuelve un objeto Call que envuelve el tipo de respuesta MovieResponse.

interface MovieApi {
    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<MovieResponse>
}