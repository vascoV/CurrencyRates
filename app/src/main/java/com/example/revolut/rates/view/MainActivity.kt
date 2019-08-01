package com.example.revolut.rates.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revolut.rates.R
import com.example.revolut.rates.view.adapter.CurrenciesAdapter
import com.example.revolut.rates.viewmodel.CurrenciesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var currenciesViewModel: CurrenciesViewModel

    lateinit var currenciesAdapter: CurrenciesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currenciesViewModel = ViewModelProviders.of(this).get(CurrenciesViewModel::class.java)
        setUpRecyclerView()

        currenciesViewModel.fetchCurrencies("EUR").observe(this, androidx.lifecycle.Observer { result ->
            populateRecyclerView(result)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setUpRecyclerView() {
        currenciesAdapter = CurrenciesAdapter(Collections.emptyList())
        currencies_rv.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        currencies_rv.layoutManager = layoutManager
        currencies_rv.adapter = currenciesAdapter

    }

    private fun populateRecyclerView(currencies: List<Pair<String, Double>>) {
        currenciesAdapter.setCurrencies(currencies)

    }
}
