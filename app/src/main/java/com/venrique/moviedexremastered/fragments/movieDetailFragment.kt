package com.venrique.moviedexremastered.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.venrique.moviedexremastered.MovieViewerActivity

import com.venrique.moviedexremastered.R
import com.venrique.moviedexremastered.database.entidades.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [movieDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [movieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class movieDetailFragment : Fragment() {

    var movie= Movie()



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_movie_detail, container, false)
        bindData(view)
        return view

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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

    fun bindData(view: View){
        view.movie_title_main_content_fragment.text=movie.title
        view.movie_rate_main_content_fragment.text=movie.rating
        view.released_main_content_fragment.text=movie.year
        view.genre_main_content_fragment.text=movie.genre
        view.runtime_main_content_fragment.text=movie.director
        Glide.with(view).load(movie.poster)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment movieDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(movie: Movie): movieDetailFragment{
            val newFragment = movieDetailFragment()
            newFragment.movie = movie
            return newFragment
        }
    }
}
