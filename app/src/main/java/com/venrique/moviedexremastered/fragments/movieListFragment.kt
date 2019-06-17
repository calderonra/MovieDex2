package com.venrique.moviedexremastered.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.venrique.moviedexremastered.MovieViewerActivity

import com.venrique.moviedexremastered.R
import com.venrique.moviedexremastered.Viewmodel.MovieViewModel
import com.venrique.moviedexremastered.adapter.movieAdapter
import com.venrique.moviedexremastered.database.entidades.Movie
import com.venrique.moviedexremastered.interfaces.IComunicaFragments
import kotlinx.android.synthetic.main.fragment_movie_list.*
import java.util.ArrayList
import kotlin.math.absoluteValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [movieListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [movieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class movieListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var adapter: movieAdapter
    lateinit var viewModel: MovieViewModel
    lateinit var title: EditText
    lateinit var btn_search:Button
    lateinit var recycleMovie: RecyclerView
    lateinit var interfaz: IComunicaFragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_movie_list, container, false)
        adapter = movieAdapter(ArrayList())
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        recycleMovie = vista.findViewById(R.id.rv_movies)
        title = vista.findViewById(R.id.et_movie)
        btn_search = vista.findViewById(R.id.search)

        recycleMovie.apply {
            adapter = this@movieListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.getAll().observe(this, Observer { peliculas->
            peliculas?.let {
                adapter.updateList(it)
                adapter.setOnClickListener(View.OnClickListener {
                    interfaz.enviarMovie(peliculas.get(recycleMovie.getChildAdapterPosition(it)))
                })
            }
        })

        btn_search.setOnClickListener {
            val MovieName = title.text.toString()
            if(MovieName.isNotEmpty() && MovieName.isNotBlank()){
                viewModel.assignMovieNameToQuery(MovieName)
                viewModel.ListResult.observe(this, Observer { lista ->
                    adapter.updateList(lista)
                })
            }
            //viewModel.retrieveRepo(title.text.toString())
        }

        return vista
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is Activity){
            interfaz = context as IComunicaFragments
        }

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment movieListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            movieListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

