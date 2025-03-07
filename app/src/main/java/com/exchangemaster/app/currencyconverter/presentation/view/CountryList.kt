package com.exchangemaster.app.currencyconverter.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exchangemaster.app.currencyconverter.R
import com.exchangemaster.app.currencyconverter.data.model.CurrencyItem
import com.exchangemaster.app.currencyconverter.presentation.adapter.CurrencyAdapter
import com.exchangemaster.app.currencyconverter.presentation.viewmodel.CountryListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CountryList : AppCompatActivity(), CurrencyAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CurrencyAdapter
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar

    private val viewModel: CountryListViewModel by viewModels()
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))
        progressBar = findViewById(R.id.progressBar)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        recyclerView = findViewById(R.id.countryList)
        searchView = findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CurrencyAdapter(emptyList(), this)
        recyclerView.adapter = adapter
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        viewModel.symbols.observe(this) { symbols ->
            val sortedSymbols = symbols.entries.sortedBy { it.key }
            val items = mutableListOf<CurrencyItem>()

            var currentChar: Char? = null
            for ((symbol, name) in sortedSymbols) {
                val firstChar = symbol.first()
                if (firstChar != currentChar) {
                    currentChar = firstChar
                    items.add(CurrencyItem.Header(currentChar))
                }
                items.add(CurrencyItem.Item(symbol, name))
            }

            adapter.updateItems(items)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
        viewModel.fetchSymbols()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onItemClick(symbol: String, name: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("symbol", symbol)
        resultIntent.putExtra("countryName", name)
        resultIntent.putExtra("Country1", intent.getBooleanExtra("Country1", false))
        resultIntent.putExtra("Country2", intent.getBooleanExtra("Country2", false))
        setResult(RESULT_OK, resultIntent)
        finish()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Handle back button click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
