package com.example.frd.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frd.api.ApiClient
import com.example.frd.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    fun getProduct(id: String): LiveData<Product> {
        val product = MutableLiveData<Product>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getProductById(id)
            product.postValue(response.body()!!)
        }
        return product
}
}