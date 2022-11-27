package ru.bullyboo.domain.interactors

import io.reactivex.Completable

interface BaseInteractor {

    fun hasTokens(): Boolean

    fun refreshAccessToken(): Completable
}