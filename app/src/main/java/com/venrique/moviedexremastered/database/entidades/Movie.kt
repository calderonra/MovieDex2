package com.venrique.moviedexremastered.database.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(tableName = "peliculas")
data class Movie(
    @PrimaryKey
    @field:Json(name = "imdbID")
    val id: String = "N/A",
    @field:Json(name = "Title")
    val title: String = "N/A",
    @field:Json(name = "Year")
    val year: String= "N/A",
    @field:Json(name = "Genre")
    val genre: String= "N/A",
    @field:Json(name = "Director")
    val director: String= "N/A",
    @field:Json(name = "Poster")
    val poster: String= "N/A",
    @field:Json(name = "Rated")
    val rating: String= "N/A",
    @field:Json(name = "Plot")
    val plot: String= "N/A",
    @field:Json(name= "Runtime")
    val duration: String= "N/A"


): Serializable

data class MovieResponse(
    @field:Json(name = "Search")
    val search: List<Movie> = listOf<Movie>(),
    @field:Json(name = "totalResults")
    val results: String
)