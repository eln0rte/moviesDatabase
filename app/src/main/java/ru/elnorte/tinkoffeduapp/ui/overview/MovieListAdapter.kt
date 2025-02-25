package ru.elnorte.tinkoffeduapp.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.elnorte.tinkoffeduapp.R
import ru.elnorte.tinkoffeduapp.databinding.MovieItemCardBinding
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

class MovieListAdapter(
    private val clickListener: MovieClickListener
) : ListAdapter<MovieOverviewDataModel, MovieListAdapter.ViewHolder>(MovieDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    class ViewHolder private constructor(
        private val binding: MovieItemCardBinding,
        private val clickListener: MovieClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieOverviewDataModel) {
            with(binding) {
                // Заполнение данных
                movieName.text = item.title
                movieInfo.text = item.info

                // Загрузка изображения
                loadImage(item.poster)

                // Обработка избранного
                star.visibility = if (item.isFavourite) View.VISIBLE else View.GONE

                // Обработчики кликов
                root.setOnClickListener { clickListener.onClick(item.id) }
                root.setOnLongClickListener {
                    clickListener.onLongClick(item.id)
                    true
                }
            }
        }
        private fun loadImage(url: String?) {
            // Реализация загрузки изображения с помощью Glide/Picasso
            url?.let {
                Glide.with(binding.root.context)
                    .load(it)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.download_fail_image)
                    .into(binding.movieBanner)
            }
        }
        companion object {
            fun from(parent: ViewGroup, clickListener: MovieClickListener): ViewHolder { // Добавляем параметр
                val binding = MovieItemCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding, clickListener) // Передаем listener
            }
        }
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<MovieOverviewDataModel>() {
    override fun areItemsTheSame(
        oldItem: MovieOverviewDataModel,
        newItem: MovieOverviewDataModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MovieOverviewDataModel,
        newItem: MovieOverviewDataModel
    ): Boolean = oldItem == newItem
}

class MovieClickListener(
    private val clickListener: (movieId: Int) -> Unit,
    private val longClickListener: (movieId: Int) -> Unit
) {
    fun onClick(movieId: Int) = clickListener(movieId)
    fun onLongClick(movieId: Int) = longClickListener(movieId)
}
