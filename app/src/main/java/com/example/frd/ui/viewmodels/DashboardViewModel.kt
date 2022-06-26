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
     fun getProducts(): LiveData<MutableList<Product>> {
        val products = MutableLiveData<MutableList<Product>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getProducts()
            products.postValue(response.body()!!)
        }
        return products
    }
    fun getProductByID(id: String): LiveData<MutableList<Product>> {
        val product = MutableLiveData<MutableList<Product>>()
        val mutableList = mutableListOf<Product>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getProductById(id)
            mutableList.add(response.body()!!)
            product.postValue(mutableList)
        }
        return product
    }
}