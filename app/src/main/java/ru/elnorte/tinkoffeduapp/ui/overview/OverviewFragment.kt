package ru.elnorte.tinkoffeduapp.ui.overview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.elnorte.tinkoffeduapp.MainApplication
import ru.elnorte.tinkoffeduapp.R
import ru.elnorte.tinkoffeduapp.databinding.OverviewFragmentBinding
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.utils.ellog
import javax.inject.Inject

class OverviewFragment : Fragment() {
    private var _binding: OverviewFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: OverviewViewModelFactory

    private val viewModel: OverviewViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = OverviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupClickListeners()
        setupObservers()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupAdapter() {
        val adapter = MovieListAdapter(
            MovieClickListener(
                { viewModel.navigateToMovie(it) },
                { viewModel.toggleFavorite(it) }
            )
        )
        binding.moviesRecyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.run {
            popularButton.setOnClickListener {
                viewModel.switchToOverview()
            }
            favoritesButton.setOnClickListener {
                viewModel.switchToFavs()
            }
            topPanel.searchIcon.setOnClickListener {
                viewModel.updateSearchQuery("")
            }

            topPanel.searchTextField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //This method is not used
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //This method is not used
                }

                override fun afterTextChanged(s: Editable?) {
                    viewModel.updateSearchQuery(s.toString())
                }
            })
        }
    }

    private fun toggleSearchView(showSearch: Boolean) {
        with(binding.topPanel) {
            titleTextView.visibility = if (showSearch) View.GONE else View.VISIBLE
            searchTextField.visibility = if (showSearch) View.VISIBLE else View.GONE

            if (showSearch) {
                searchTextField.requestFocus()
                val imm = requireContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(searchTextField, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effects.collect { effect ->
                    when (effect) {
                        is OverviewEffect.NavigateToMovie -> navToDetails(effect.movieId)
                    }
                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            ellog(state)
            when (state) {
                OverviewUiState.Loading -> showLoadingState()
                is OverviewUiState.Success -> showSuccess(state)
                is OverviewUiState.Error -> showError(state.error)
            }
        }
    }

    private fun showSuccess(state: OverviewUiState.Success) {
        showContent(state.list)
        toggleSearchView(state.searchShow)

        binding.topPanel.titleTextView.text = when (state.fragment) {
            FragmentType.FAVORITE -> getString(R.string.favorites_title)
            FragmentType.OVERVIEW -> getString(R.string.popular_title)
        }
    }

    private fun showContent(data: List<MovieOverviewDataModel>) = with(binding) {
        bottomBar.visibility = View.VISIBLE
        loadingState.root.visibility = View.GONE
        errorState.root.visibility = View.GONE

        if (data.isNotEmpty()) {
            (moviesRecyclerView.adapter as? MovieListAdapter)?.submitList(data)
            emptyListTab.root.visibility = View.GONE
            moviesRecyclerView.visibility = View.VISIBLE
        } else {
            moviesRecyclerView.visibility = View.GONE
            emptyListTab.root.visibility = View.VISIBLE
        }
    }

    private fun showLoadingState() = with(binding) {
        loadingState.root.visibility = View.VISIBLE
        moviesRecyclerView.visibility = View.GONE
        errorState.root.visibility = View.GONE
        emptyListTab.root.visibility = View.GONE
        bottomBar.visibility = View.GONE

    }

    private fun navToDetails(movieId: Int) {
        findNavController().navigate(
            OverviewFragmentDirections.actionShowMovie(movieId)
        )
    }

    private fun showError(error: String?) = with(binding) {
        loadingState.root.visibility = View.GONE
        moviesRecyclerView.visibility = View.GONE
        errorState.root.visibility = View.VISIBLE
        emptyListTab.root.visibility = View.GONE
        bottomBar.visibility = View.GONE

        errorState.textView2.text = error ?: getString(R.string.unknown_error)
        errorState.button.setOnClickListener { viewModel.retry() }
    }
}
