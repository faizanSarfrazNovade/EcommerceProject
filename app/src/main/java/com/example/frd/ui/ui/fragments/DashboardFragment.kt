package com.example.frd.ui.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frd.R
import com.example.frd.models.Product
import com.example.frd.ui.activities.ProductDetailsActivity
import com.example.frd.ui.activities.SettingsActivity
import com.example.frd.ui.adapters.DashboardItemsListAdapter
import com.example.frd.ui.viewmodels.DashboardViewModel
import com.example.frd.utils.Constants
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {
    private lateinit var dashboardViewModel: DashboardViewModel
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
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        dashboardViewModel.getProducts().observe(viewLifecycleOwner, { products ->
            val adapter = activity?.let { DashboardItemsListAdapter(it, products) }
            val rvProducts = root.findViewById<RecyclerView>(R.id.rv_dashboard_items)
            rvProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvProducts?.adapter = adapter
            successDashboardItemsList(products)
        })

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
    }

    fun successDashboardItemsList(dashboardItemsList: MutableList<Product>) {

        if (dashboardItemsList.size ?: 0 > 0) {

            rv_dashboard_items.visibility = View.VISIBLE
            tv_no_dashboard_items_found.visibility = View.GONE

            rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
            rv_dashboard_items.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            rv_dashboard_items.adapter = adapter

            adapter.setOnClickListener(object :
                DashboardItemsListAdapter.OnClickListener {
                override fun onClick(position: Int, product: Product) {

                    // Launch the product details screen from the dashboard.
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.id)
                    startActivity(intent)
                }
            })

        } else {
            rv_dashboard_items.visibility = View.GONE
            tv_no_dashboard_items_found.visibility = View.VISIBLE
        }
    }

}