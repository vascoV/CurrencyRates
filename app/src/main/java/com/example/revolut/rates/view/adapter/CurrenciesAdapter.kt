package com.example.revolut.rates.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut.rates.R
import com.example.revolut.rates.common.StringToDouble
import com.example.revolut.rates.common.currenciesNames
import com.example.revolut.rates.common.roundedString
import com.example.revolut.rates.data.model.Rate
import com.example.revolut.rates.view.NotifyCurrencies
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.rates_item.view.*


class CurrenciesAdapter(
    private val parentNotifier: NotifyCurrencies
) : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesViewHolder>() {

    private var currenciesList: ArrayList<Rate>? = null

    private var currentAmount: Double = 0.0

    private var currentBase = MutableLiveData<String>()

    private val currenciesNameMap = currenciesNames

    init {
        currentBase.observe(parentNotifier as LifecycleOwner, Observer {
            parentNotifier.newBaseNotify(it)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        return CurrenciesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rates_item, parent, false))
    }

    override fun getItemCount(): Int {
        return currenciesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(position)
    }

    private fun setInitBase(base: String) {
        if (currenciesList?.any { it.code == base } == false) {
            currenciesList?.add(0, Rate(base, 0.0))
        }

        updateCurrentBase(base)
    }

    fun updateCurrencies(base: String, ratesMap: Map<String, Double>) {

        if (currenciesList == null) {
            currenciesList = ArrayList(ratesMap.map {
                Rate(it.key, it.value)
            })
            notifyDataSetChanged()
        } else {
            currenciesList?.forEach {
                it.rate = ratesMap[it.code]
            }
        }

        if (currentBase.value == null) {
            setInitBase(base)
        }

        notifyItemRangeChanged(1, itemCount, currentAmount)
    }

    fun updateCurrentBase(base: String) {
        currentBase.value = base
    }

    private fun getCopyofCurrencies(position: Int): Rate {
        return currenciesList?.get(position)?.copy() ?: Rate.EMPTY
    }


    inner class CurrenciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val currencyFlag: ImageView = itemView.currency_image
        private val currencyCode: TextView = itemView.currency_code
        private val currencySymbol: TextView = itemView.currency_name
        private val currencyAmountEditText: EditText = itemView.currency_value

        fun bind(position: Int) {
            val rate = getCopyofCurrencies(position)
            renderItems(rate)
            editTextFocus(rate, position)
            amountObserver()
        }

        private fun renderItems(rate: Rate) {
            val imageId = itemView.resources.getIdentifier(
                "icon_" + (rate.code)!!.toLowerCase(),
                "drawable",
                itemView.context.packageName
            )
            currencyCode.text = rate.code
            currencySymbol.text = currenciesNameMap.getValue(rate.code!!)
            currencyFlag.setImageResource(imageId)

            if (rate.code != currentBase.value) {
                currencyAmountEditText.setText(((rate.rate ?: 0.0) * currentAmount).roundedString())
            } else {
                currencyAmountEditText.setText(currentAmount.toString())
            }
        }

        private fun editTextFocus(rate: Rate, position: Int) {
            currencyAmountEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) return@setOnFocusChangeListener

                if (position != 0) {
                    currenciesList?.removeAt(position).also {
                        currenciesList?.add(0, rate)
                    }

                    android.os.Handler().post {
                        notifyItemMoved(position, 0)
                    }

                    updateCurrentBase(rate.code!!)
                }
            }
        }

        private fun amountObserver() {
            apply {
                currencyAmountEditText.textChanges().subscribe {
                    if (currencyAmountEditText.isFocused) {
                        currentAmount = it.toString().StringToDouble()
                    }
                }
            }
        }
    }
}