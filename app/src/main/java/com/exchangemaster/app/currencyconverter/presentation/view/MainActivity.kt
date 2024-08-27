package com.exchangemaster.app.currencyconverter.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.exchangemaster.app.currencyconverter.R
import com.exchangemaster.app.currencyconverter.presentation.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var convert: ImageView
    private lateinit var country1: TextView
    private lateinit var country2: TextView
    private lateinit var countryValue1: TextView
    private lateinit var countryValue2: TextView
    private lateinit var countrySymbol1: TextView
    private lateinit var countrySymbol2: TextView
    private lateinit var country1Convert: TextView
    private lateinit var country2Convert: TextView
    private lateinit var change1:LinearLayout
    private lateinit var change2:LinearLayout

    private val dailer1Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.getStringExtra("Dailer1Value")?.let {
                if(countryValue1.text.toString() != it)
                {
                    viewModel.countryValue1.value = it
                    convert.rotation = 0.0F
                    convert()

                }

            }
        }
    }

    private val dailer2Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.getStringExtra("Dailer2Value")?.let {
                if(countryValue2.text.toString() != it)
                {
                    viewModel.countryValue2.value = it
                    convert.rotation = 180.0F
                    convert()

                }
            }
        }
    }

    private val countryListLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val symbol = result.data?.getStringExtra("symbol")
            val countryName = result.data?.getStringExtra("countryName")
            if (symbol != null && countryName != null) {
                if (result.data?.getBooleanExtra("Country1", false) == true) {
                    if(countrySymbol2.text != symbol)
                    {
                        countrySymbol1.text = symbol
                        viewModel.country1.value = countryName
                        convert()

                    }
                    else
                    {
                        Toast.makeText(this,"please select different countries",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    if(countrySymbol1.text != symbol)
                    {
                        countrySymbol2.text = symbol
                        viewModel.country2.value = countryName
                        convert()

                    }
                    else
                    {
                        Toast.makeText(this,"please select different countries",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        convert = findViewById(R.id.imageViewConvert)
        country1 = findViewById(R.id.Country1)
        country2 = findViewById(R.id.Country2)
        countryValue1 = findViewById(R.id.currencyValue1)
        countryValue2 = findViewById(R.id.currencyValue2)
        countrySymbol1 = findViewById(R.id.currencySymbol1)
        countrySymbol2 = findViewById(R.id.currencySymbol2)
        country1Convert = findViewById(R.id.country1Indicator)
        country2Convert = findViewById(R.id.country2Indicator)
        change1 = findViewById(R.id.changeC1)
        change2 = findViewById(R.id.changeC2)

        /*convert.setOnClickListener {
            convert.rotation += 180
            convert.rotation %= 360
            convert()
        }*/


        countryValue1.setOnClickListener {
            val intent = Intent(this, Dailer1::class.java)
            intent.putExtra("Country1Value", countryValue1.text.toString().trim())
            dailer1Launcher.launch(intent)
        }

        countryValue2.setOnClickListener {
            val intent = Intent(this, Dailer2::class.java)
            intent.putExtra("Country2Value", countryValue2.text.toString().trim())
            dailer2Launcher.launch(intent)
        }

        change1.setOnClickListener {
            val intent = Intent(this, CountryList::class.java)
            intent.putExtra("Country1", true)
            countryListLauncher.launch(intent)
        }

        change2.setOnClickListener {
            val intent = Intent(this, CountryList::class.java)
            intent.putExtra("Country2", true)
            countryListLauncher.launch(intent)
        }

        viewModel.countryValue1.observe(this, Observer { newValue ->
            countryValue1.text = newValue
        })
        viewModel.countryValue2.observe(this, Observer { newValue ->
            countryValue2.text = newValue
        })
        viewModel.country1.observe(this, Observer { newValue ->

                country1.text = newValue

        })
        viewModel.country2.observe(this, Observer { newValue ->
                country2.text = newValue
        })
        viewModel.exchange1.observe(this, Observer { newValue ->
            country1Convert.text = "$newValue ${countrySymbol1.text}"
        })
        viewModel.exchange2.observe(this, Observer { newValue ->
            country2Convert.text = "$newValue ${countrySymbol2.text}"
        })
    }

    private fun convert() {
        if (convert.rotation == 0.0F) {
            viewModel.getExchangeRate(countrySymbol2.text.toString(), countrySymbol1.text.toString(), "country1")
            viewModel.convertExchangeRate(countrySymbol2.text.toString(), countrySymbol1.text.toString(), countryValue1.text.toString().toDouble(), "country2")
        } else if (convert.rotation == 180.0F) {
            viewModel.getExchangeRate(countrySymbol1.text.toString(), countrySymbol2.text.toString(), "country2")
            viewModel.convertExchangeRate(countrySymbol1.text.toString(), countrySymbol2.text.toString(), countryValue2.text.toString().toDouble(), "country1")
        }
    }

    override fun onResume() {
        convert()
        super.onResume()
    }
}
