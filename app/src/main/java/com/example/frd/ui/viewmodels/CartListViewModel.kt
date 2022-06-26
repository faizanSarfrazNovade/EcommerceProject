package com.example.frd.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartListViewModel: ViewModel() {
    var numberInCart = 0

    val currentNumber: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>(0)
    }
}