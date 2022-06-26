package com.example.frd.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frd.api.ApiClient
import com.example.frd.models.Cart2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel: ViewModel() {
    fun getCart(id: String): LiveData<Cart2>{
        val cart = MutableLiveData<Cart2>()
        CoroutineScope(Dispatchers.IO).launch {
            val res = ApiClient.apiService.getCart(id)
            cart.postValue(res.body()!!)
        }
        return cart
    }
}