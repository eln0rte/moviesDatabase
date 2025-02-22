package ru.elnorte.tinkoffeduapp.ui.movieinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.elnorte.tinkoffeduapp.MainApplication
import ru.elnorte.tinkoffeduapp.R
import ru.elnorte.tinkoffeduapp.databinding.MovieinfoFragmentBinding
import javax.inject.Inject

class MovieFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory

    val viewModel: MovieViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MovieinfoFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.movieinfo_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.backArrow.setOnClickListener { this.findNavController().navigateUp() }
        val arguments = MovieFragmentArgs.fromBundle(requireArguments())
        viewModel.fetchMovieDetails(arguments.movieId)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }
}
