package ru.elnorte.tinkoffeduapp.ui.models

data class MovieOverviewDataModel(
    val id: Int,
    val poster: String,
    val title: String,
    val info: String,
    val isFavourite:Boolean,
)
