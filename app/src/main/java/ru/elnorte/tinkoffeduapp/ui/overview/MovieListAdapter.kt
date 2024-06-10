package ru.elnorte.tinkoffeduapp.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.elnorte.tinkoffeduapp.databinding.MovieItemCardBinding
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

class MovieListAdapter(val clickListener: MovieClickListener) :
    ListAdapter<MovieOverviewDataModel, MovieListAdapter.ViewHolder>(MovieDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: MovieItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieOverviewDataModel, clickListener: MovieClickListener) {
            binding.movieItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieItemCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class MovieDiffCallBack : DiffUtil.ItemCallback<MovieOverviewDataModel>() {

    override fun areItemsTheSame(
        oldItem: MovieOverviewDataModel,
        newItem: MovieOverviewDataModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieOverviewDataModel,
        newItem: MovieOverviewDataModel
    ): Boolean {
        return oldItem == newItem
    }
}


class MovieClickListener(val clickListener: (movieId: Int) -> Unit,val longClickListener: (movieId: Int) -> Unit) {
    fun onClick(movie: MovieOverviewDataModel) = clickListener(movie.id)
    fun onLongClick(movie: MovieOverviewDataModel): Boolean {longClickListener(movie.id);return true}
}