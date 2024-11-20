package com.example.invest

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.invest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val text = "InvestEasy"
        val spannableString = SpannableString(text)

        spannableString.setSpan(
            StyleSpan(Typeface.BOLD_ITALIC),
            0,
            10,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.title.text = spannableString

        binding.btnLimpar.setOnClickListener {
            binding.tieValue.setText("")
            binding.tieMonth.setText("")
            binding.tieFees.setText("")
            binding.tvResult.text = ""
            binding.tvResultFees.text = ""
        }

        binding.btnCalcular.setOnClickListener {
            val valueTemp = binding.tieValue.text
            val monthTemp = binding.tieMonth.text
            val feesTemp = binding.tieFees.text


            if (valueTemp?.isEmpty() == true ||
                monthTemp?.isEmpty() == true ||
                feesTemp?.isEmpty() == true
            ){
                Snackbar
                    .make(binding.tieValue, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                    .show()

            } else {
                val value = valueTemp.toString().toFloat()
                val month = monthTemp.toString().toInt()
                val fees = feesTemp.toString().toFloat()

                val feesDecimal: Float = fees / 100
                val valueMonth: Float = (1f + feesDecimal).pow(month) - 1
                val result: Float = value * (valueMonth / feesDecimal)

                val totalInverted = value * month
                val totalFees = result - totalInverted

                binding.tvResult.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(result)
                binding.tvResultFees.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(totalFees)

            }
        }
    }
}