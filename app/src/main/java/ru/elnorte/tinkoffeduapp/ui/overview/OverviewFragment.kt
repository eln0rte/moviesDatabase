package ru.elnorte.tinkoffeduapp.ui.overview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.elnorte.tinkoffeduapp.MainApplication
import ru.elnorte.tinkoffeduapp.R
import ru.elnorte.tinkoffeduapp.databinding.OverviewFragmentBinding
import javax.inject.Inject

class OverviewFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: OverviewViewModelFactory

    val viewModel: OverviewViewModel by viewModels{viewModelFactory}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: OverviewFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.overview_fragment, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val adapter = MovieListAdapter(
            MovieClickListener({
                viewModel.showMovie(it)
            },
                {
                    viewModel.addFavourite(it)
                })
        )
        binding.moviesList.adapter = adapter

        try {
            viewModel.update()
            Log.d("ellog3", "hi")
        } catch (ex: Exception) {
            Log.d("ellog3", ex.message.orEmpty())
        }

        viewModel.navigateToMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController().navigate(OverviewFragmentDirections.actionShowMovie(it))
                viewModel.showMovieComplete()
            }
        }
        viewModel.model.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }
}
