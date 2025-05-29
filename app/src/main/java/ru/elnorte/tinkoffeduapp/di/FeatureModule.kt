package ru.elnorte.tinkoffeduapp.di

import dagger.Module
import ru.elnorte.features.di.FeatureComponent

@Module(
    subcomponents = [FeatureComponent::class]
)
class FeatureModule
