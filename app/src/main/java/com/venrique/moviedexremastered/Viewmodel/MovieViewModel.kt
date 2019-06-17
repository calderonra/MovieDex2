package com.venrique.moviedexremastered.Viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.venrique.moviedexremastered.database.DB.MovieDatabase
import com.venrique.moviedexremastered.database.entidades.Movie
import com.venrique.moviedexremastered.movieRepository.MovieRepo
import com.venrique.moviedexremastered.retrofit.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel (private val app: Application) : AndroidViewModel(app){
    private val repository:MovieRepo
    private val SearchedMovieslist = MutableLiveData<MutableList<Movie>>()
    private val query = MutableLiveData<String>()

    init {
        val movieDAO=MovieDatabase.getInstance(app).movieDao()
        val movieService = MovieService.getGithubService()
        repository= MovieRepo(movieDAO, movieService)
    }

    private suspend fun insert(peli: Movie)=repository.insert(peli)


    fun retrieveRepo(user:String) = viewModelScope.launch(Dispatchers.IO) {
        this@MovieViewModel.nuke()
        val response=repository.retrieveReposAsync(user).await()

        if(response.isSuccessful) with(response){
            this.body()?.search?.forEach {

                val respuesta = repository.retrieveMovieAsync(it.id).await()

                if (respuesta.isSuccessful) with(respuesta){
                    Log.d("Titulo",it.title)
                    this@MovieViewModel.insert(this.body()!!)
                    SearchedMovieslist.postValue(response.body()?.search?.toMutableList()?:arrayListOf(Movie()))

                }else with(respuesta){
                    when(respuesta.code()){
                        404->{
                            Toast.makeText(app, "F", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }else with(response){
            when(response.code()){
                404->{
                    Toast.makeText(app, "F", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun getAll(): LiveData<List<Movie>> {
        return repository.getAll()
    }


/*    fun getAllT(): LiveData<List<Movie>> = Transformations.switchMap(query){
        retrieveRepo(it)
    }*/

    fun MoviesList(name: String): LiveData<MutableList<Movie>>{
        retrieveRepo(name)
        return SearchedMovieslist
    }

    val ListResult: LiveData<MutableList<Movie>> = Transformations.switchMap(
        query,
        ::MoviesList
    )
    fun assignMovieNameToQuery(name: String) = apply { query.value = name }

    private suspend fun nuke()= repository.nuke()
}