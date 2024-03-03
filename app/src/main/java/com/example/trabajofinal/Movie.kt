package com.example.trabajofinal

import android.os.Parcel
import android.os.Parcelable




data class Movie(
    val id: Int,
    val rating: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
) : Parcelable {

    // Métodos necesarios para la implementación de Parcelable
    override fun writeToParcel(dest: Parcel, flags: Int) {
        // Escribir los datos de la película en el Parcel
        dest.writeInt(id)
        dest.writeInt(rating)
        dest.writeString(title)
        dest.writeString(posterPath)
        dest.writeString(overview)
        dest.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        // Este método es requerido pero no lo utilizamos aquí, por lo que devolvemos 0
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            // Leer los datos de la película desde el Parcel y crear una nueva instancia de Movie
            return Movie(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
            )
        }

        override fun newArray(size: Int): Array<Movie?> {
            // Este método también es requerido pero no lo utilizamos aquí,por lo tanto devolvemos un array vacío
            return arrayOfNulls(size)
        }
    }
}

