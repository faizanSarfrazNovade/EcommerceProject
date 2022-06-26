package com.example.frd.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frd.api.ApiClient
import com.example.frd.models.DeliveryAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAddressViewModel: ViewModel(){
    fun getAddresses(id: String): LiveData<MutableList<DeliveryAddress>> {
        val addresses = MutableLiveData<MutableList<DeliveryAddress>>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getAddress(id)
            addresses.postValue(response.body()!!)
        }
        return addresses
    }
}