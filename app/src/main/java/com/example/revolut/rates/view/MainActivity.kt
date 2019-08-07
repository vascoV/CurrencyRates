package com.example.revolut.rates.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revolut.rates.BR
import com.example.revolut.rates.R
import com.example.revolut.rates.view.adapter.CurrenciesAdapter
import com.example.revolut.rates.viewmodel.CurrenciesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NotifyCurrencies {

    private lateinit var currenciesViewModel: CurrenciesViewModel

    private lateinit var currenciesAdapter: CurrenciesAdapter

    private lateinit var viewDataBinding: ViewDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currenciesViewModel = ViewModelProviders.of(this).get(CurrenciesViewModel::class.java)
        currenciesViewModel.setNotifier(this)

        initBinding()

        setUpRecyclerView()
        newBaseNotify(CurrenciesViewModel.defaultCurrency)
    }

    private fun setUpRecyclerView() {
        currenciesAdapter = CurrenciesAdapter(this)
        currencies_rv.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        currencies_rv.layoutManager = layoutManager
        currencies_rv.adapter = currenciesAdapter

        currencies_rv.setOnTouchListener { v, event ->
            this.hideKeyboard()
            false
        }
    }

    private fun initBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewDataBinding.setVariable(BR.viewModel, currenciesViewModel)
        viewDataBinding.executePendingBindings()
    }


    override fun newBaseNotify(base: String) {
        if (currenciesViewModel.baseCurrency.value != base) {
            currenciesViewModel.baseCurrency.value = base
        }
    }

    override fun updateCurrenciesRateItems(base: String, ratesMap: Map<String, Double>) {
        currenciesAdapter.updateCurrencies(base, ratesMap)
    }

    override fun showErrorMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
