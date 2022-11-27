package ru.bullyboo.core_ui.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias Inflate = (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding

typealias InflateType<V> = (LayoutInflater, ViewGroup?, Boolean) -> V