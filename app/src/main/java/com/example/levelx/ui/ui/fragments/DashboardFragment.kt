package com.example.levelx.ui.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import com.example.levelx.R
import com.example.levelx.api.ApiClient
import com.example.levelx.ui.activities.SettingsActivity

class DashboardFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)*/

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        ApiClient.apiService.getProducts();
        hideProgressDialog()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }

            R.id.action_cart -> {
                startActivity(Intent(activity, CartListFragment::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        getDashboardItemsList()
    }

}