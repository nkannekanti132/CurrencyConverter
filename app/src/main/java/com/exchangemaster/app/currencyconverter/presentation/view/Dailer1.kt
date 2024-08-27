package com.exchangemaster.app.currencyconverter.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exchangemaster.app.currencyconverter.R


class Dailer1 : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var button10: Button
    private lateinit var button11: ImageView
    private lateinit var button12: Button

    private lateinit var down:TextView


    private lateinit var mValue:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailer1)

        button = findViewById(R.id.button)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        button10 = findViewById(R.id.button10)
        button11 = findViewById(R.id.imageView)
        button12 = findViewById(R.id.button12)
        mValue = findViewById(R.id.valueM1)
        down = findViewById(R.id.down)

        onClicker(button)
        onClicker(button1)
        onClicker(button2)
        onClicker(button3)
        onClicker(button4)
        onClicker(button5)
        onClicker(button6)
        onClicker(button7)
        onClicker(button8)
        onClicker(button9)
        onClicker(button10)




        if(mValue.text == "0")
        {
            mValue.text = ""
        }

        button11.setOnClickListener {
            if(mValue.text.toString().isNotEmpty())
            {
                mValue.text = mValue.text.toString().substring(0,mValue.text.toString().length-1)
            }

        }

        button12.setOnClickListener {
            val resultIntent = Intent()
            if (mValue.text.toString().isEmpty())
            {
                resultIntent.putExtra("Dailer1Value", "0")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            else if (mValue.text.toString().toDoubleOrNull()==null)
            {
                Toast.makeText(this,"Please enter a valid number",Toast.LENGTH_SHORT).show()
            }
            else
            {
                resultIntent.putExtra("Dailer1Value", mValue.text.toString())
                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            }

        }

        down.setOnClickListener {
            val resultIntent = Intent()
            if (mValue.text.toString().isEmpty())
            {
                resultIntent.putExtra("Dailer1Value", "0")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            else if (mValue.text.toString().toDoubleOrNull()==null)
            {
                Toast.makeText(this,"Please enter a valid number",Toast.LENGTH_SHORT).show()
            }
            else
            {
                resultIntent.putExtra("Dailer1Value", mValue.text.toString())
                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            }


        }


    }

    private fun onClicker(button: Button)
    {
        button.setOnClickListener {
                mValue.text = mValue.text.toString() + button.text.toString()
        }
    }

    override fun onResume() {
        if(intent.getStringExtra("Country1Value")!="0")
        {
            mValue.text = intent.getStringExtra("Country1Value")
        }

        super.onResume()
    }

}