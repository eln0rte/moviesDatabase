package ru.elnorte.tinkoffeduapp.ui.movieinfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.elnorte.tinkoffeduapp.R
import ru.elnorte.tinkoffeduapp.data.movierepository.MovieRepository
import ru.elnorte.tinkoffeduapp.data.movierepository.database.FavDatabase
import ru.elnorte.tinkoffeduapp.databinding.MovieinfoFragmentBinding
import ru.elnorte.tinkoffeduapp.databinding.OverviewFragmentBinding
import ru.elnorte.tinkoffeduapp.ui.overview.OverviewFragmentDirections
import ru.elnorte.tinkoffeduapp.ui.overview.OverviewViewModel
import ru.elnorte.tinkoffeduapp.ui.overview.OverviewViewModelFactory

class MovieFragment : Fragment() {

    lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: MovieinfoFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.movieinfo_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dao = FavDatabase.getInstance(application).favDatabaseDao
        val repo = MovieRepository(dao)
        val viewModelFactory = MovieViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.backArrow.setOnClickListener { this.findNavController().navigateUp() }
        val arguments = MovieFragmentArgs.fromBundle(requireArguments())
        viewModel.fetchMovieDetails(arguments.movieId)

        return binding.root
    }
}
