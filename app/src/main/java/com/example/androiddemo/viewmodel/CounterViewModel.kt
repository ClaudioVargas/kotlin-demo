package com.example.androiddemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Contador con LiveData. El valor sobrevive a rotaciones de pantalla
 * porque el ViewModel se conserva entre configuraciones.
 */
class CounterViewModel : ViewModel() {

    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    fun increment() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun decrement() {
        _count.value = (_count.value ?: 0) - 1
    }

    fun reset() {
        _count.value = 0
    }
}