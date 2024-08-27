package com.exchangemaster.app.currencyconverter.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exchangemaster.app.currencyconverter.R
import com.exchangemaster.app.currencyconverter.data.model.CurrencyItem

class CurrencyAdapter(private var items: List<CurrencyItem>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), android.widget.Filterable {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    private var itemsFiltered: List<CurrencyItem> = items

    interface OnItemClickListener {
        fun onItemClick(symbol: String, name: String)
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemsFiltered[position]) {
            is CurrencyItem.Header -> TYPE_HEADER
            is CurrencyItem.Item -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            )
            TYPE_ITEM -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = itemsFiltered[position]) {
            is CurrencyItem.Header -> (holder as HeaderViewHolder).bind(item)
            is CurrencyItem.Item -> (holder as ItemViewHolder).bind(item, itemClickListener)
        }
    }

    override fun getItemCount(): Int = itemsFiltered.size

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.headerTextView)
        fun bind(header: CurrencyItem.Header) {
            headerTextView.text = header.letter.toString()
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symbolTextView: TextView = itemView.findViewById(R.id.symbolTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        fun bind(item: CurrencyItem.Item, clickListener: OnItemClickListener) {
            symbolTextView.text = item.symbol
            nameTextView.text = item.name
            itemView.setOnClickListener {
                clickListener.onItemClick(item.symbol, item.name)
            }
        }
    }

    fun updateItems(newItems: List<CurrencyItem>) {
        items = newItems
        itemsFiltered = newItems
        notifyDataSetChanged()
    }

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                itemsFiltered = if (charString.isEmpty()) items else {
                    val filteredList = ArrayList<CurrencyItem>()
                    var currentChar: Char? = null

                    for (item in items) {
                        if (item is CurrencyItem.Item && (item.symbol.contains(charString, true) || item.name.contains(charString, true))) {
                            val firstChar = item.symbol.first()
                            if (firstChar != currentChar) {
                                currentChar = firstChar
                                filteredList.add(CurrencyItem.Header(currentChar))
                            }
                            filteredList.add(item)
                        }
                    }
                    filteredList
                }
                return FilterResults().apply { values = itemsFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFiltered = results?.values as List<CurrencyItem>
                notifyDataSetChanged()
            }
        }
    }
}
