package ru.bullyboo.astrology.dagger.holders

interface ComponentHolder<Component> {

    fun provide(): Component
}