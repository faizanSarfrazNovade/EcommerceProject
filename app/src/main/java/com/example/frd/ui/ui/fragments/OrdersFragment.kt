package com.example.frd.ui.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frd.R

class OrdersFragment : BaseFragment() {

    /*private lateinit var notificationsViewModel: NotificationsViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)*/

        val root = inflater.inflate(R.layout.fragment_orders, container, false)
        return root
    }


    override fun onResume() {
        super.onResume()

        getMyOrdersList()
    }


    private fun getMyOrdersList() {
    }
}