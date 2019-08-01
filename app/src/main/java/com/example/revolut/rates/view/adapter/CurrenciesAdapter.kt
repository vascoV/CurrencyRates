package com.example.revolut.rates.view.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut.rates.R
import kotlinx.android.synthetic.main.rates_item.view.*

class CurrenciesAdapter(
    private var currencyList: MutableList<Pair<String, Double>>
) : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        return CurrenciesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rates_item, parent, false))
    }

    override fun getItemCount(): Int {
        return currencyList.count()
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {

        holder.bind(
            currencyList[position].first,
            currencyList[position].second
        )
    }

    inner class CurrenciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(name: String, value: Double): Unit = with(itemView) {
            val imageId = resources.getIdentifier("icon_" + name.toLowerCase(), "drawable", context.packageName)
            currency_image.setImageResource(imageId)
            currency_name.text = name
            currency_symbol.text = name
            currency_value.text = Editable.Factory.getInstance().newEditable(value.toString())
            currency_value.visibility = if (layoutPosition == 0) View.GONE else View.VISIBLE

            this.setOnClickListener {
                selectItem(this)
            }
        }

        private fun selectItem(view: View) {
            layoutPosition.takeIf { it > 0 }?.also { currenтPosition ->
                (currencyList as ArrayList<Pair<String, Double>>).removeAt(currenтPosition).also {
                    currencyList.add(0, it)
                }

                notifyItemMoved(currenтPosition, 0)
                (view.parent as RecyclerView).scrollToPosition(0)
            }
        }
    }

    fun setCurrencies(currencies: List<Pair<String, Double>>) {
        currencyList = currencies.toMutableList()
        notifyDataSetChanged()
    }
}