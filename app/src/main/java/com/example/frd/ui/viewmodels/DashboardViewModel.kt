package com.example.frd.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frd.api.ApiClient
import com.example.frd.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private fun getDashboardItemsList(): LiveData<MutableList<Product>> {
        val products = MutableLiveData<MutableList<Product>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getProducts()
            products.postValue(response.body()!!)
        }

        return products
    }
}