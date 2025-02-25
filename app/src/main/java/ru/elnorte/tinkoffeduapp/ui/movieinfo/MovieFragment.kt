package ru.elnorte.tinkoffeduapp.ui.movieinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.elnorte.tinkoffeduapp.MainApplication
import ru.elnorte.tinkoffeduapp.R
import ru.elnorte.tinkoffeduapp.databinding.MovieinfoFragmentBinding
import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel
import javax.inject.Inject

class MovieFragment : Fragment() {
    private var _binding: MovieinfoFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory

    private val viewModel: MovieViewModel by viewModels { viewModelFactory }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            MovieinfoFragmentBinding.inflate(inflater, container, false)
        binding.backArrow.setOnClickListener { this.findNavController().navigateUp() }
        val arguments = MovieFragmentArgs.fromBundle(requireArguments())
        viewModel.fetchMovieDetails(arguments.movieId)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                MovieUiState.Loading -> showLoading()
                is MovieUiState.Error -> showError(state.error)
                is MovieUiState.Success -> showContent(state.data)
            }
        }

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            movieBanner.visibility = View.GONE
            scrollableView.visibility = View.GONE
            backArrow.visibility = View.GONE
            loadingLayer.root.visibility = View.VISIBLE
            errorLayer.root.visibility = View.GONE
        }
    }

    private fun showContent(movie: MovieInfoDataModel) {
        with(binding) {
            val imgUri = movie.poster.toUri().buildUpon().scheme("https").build()
            Glide.with(movieBanner)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.download_fail_image)
                )
                //todo rethink
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(movieBanner)

            movieTitle.text = movie.title
            movieDescription.text = movie.description
            movieGenre.text = getString(R.string.genres_placeholder, movie.genre)
            movieOrigin.text = getString(R.string.countries_placeholder, movie.origin)

            movieBanner.visibility = View.VISIBLE
            scrollableView.visibility = View.VISIBLE
            backArrow.visibility = View.VISIBLE
            loadingLayer.root.visibility = View.GONE
            errorLayer.root.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        with(binding) {
            movieBanner.visibility = View.GONE
            scrollableView.visibility = View.GONE
            backArrow.visibility = View.GONE
            loadingLayer.root.visibility = View.GONE
            errorLayer.root.visibility = View.VISIBLE

            // Настройка errorLayer
            errorLayer.errorText.text = message
            errorLayer.retryButton.setOnClickListener {
                viewModel.retry()
            }
        }
    }


}
