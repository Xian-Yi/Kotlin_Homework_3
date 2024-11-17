package com.example.lab9_2_kotlin

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var btnCalculate: Button
    private lateinit var edHeight: EditText
    private lateinit var edWeight: EditText
    private lateinit var edAge: EditText
    private lateinit var tvWeightResult: TextView
    private lateinit var tvFatResult: TextView
    private lateinit var tvBmiResult: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var llProgress: LinearLayout
    private lateinit var btnBoy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 元件綁定
        initViewBindings()

        btnCalculate.setOnClickListener {
            if (validateInputs()) {
                startCalculation()
            }
        }
    }

    // 初始化元件綁定
    private fun initViewBindings() {
        btnCalculate = findViewById(R.id.btnCalculate)
        edHeight = findViewById(R.id.edHeight)
        edWeight = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvWeightResult = findViewById(R.id.tvWeightResult)
        tvFatResult = findViewById(R.id.tvFatResult)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        llProgress = findViewById(R.id.llProgress)
        btnBoy = findViewById(R.id.btnBoy)
    }

    // 驗證輸入資料是否完整
    private fun validateInputs(): Boolean {
        return when {
            edHeight.text.isEmpty() -> {
                showToast("請輸入身高")
                false
            }
            edWeight.text.isEmpty() -> {
                showToast("請輸入體重")
                false
            }
            edAge.text.isEmpty() -> {
                showToast("請輸入年齡")
                false
            }
            else -> true
        }
    }

    // 顯示 Toast 訊息
    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    // 開始執行計算
    private fun startCalculation() {
        resetResults()
        showProgress()

        Thread {
            for (progress in 1..100) {
                Thread.sleep(50)
                updateProgress(progress)
            }
            calculateAndShowResults()
        }.start()
    }

    // 重置結果
    private fun resetResults() {
        tvWeightResult.text = "標準體重\n無"
        tvFatResult.text = "體脂肪\n無"
        tvBmiResult.text = "BMI\n無"
    }

    // 顯示進度條
    private fun showProgress() {
        progressBar.progress = 0
        tvProgress.text = "0%"
        llProgress.visibility = View.VISIBLE
    }

    // 更新進度
    private fun updateProgress(progress: Int) {
        runOnUiThread {
            progressBar.progress = progress
            tvProgress.text = "$progress%"
        }
    }

    // 計算並顯示結果
    private fun calculateAndShowResults() {
        val height = edHeight.text.toString().toDouble()
        val weight = edWeight.text.toString().toDouble()
        val age = edAge.text.toString().toDouble()
        val bmi = weight / ((height / 100).pow(2))
        val (standWeight, bodyFat) = if (btnBoy.isChecked) {
            (height - 80) * 0.7 to 1.39 * bmi + 0.16 * age - 19.34
        } else {
            (height - 70) * 0.6 to 1.39 * bmi + 0.16 * age - 9
        }

        runOnUiThread {
            llProgress.visibility = View.GONE
            tvWeightResult.text = "標準體重\n${"%.2f".format(standWeight)}"
            tvFatResult.text = "體脂肪\n${"%.2f".format(bodyFat)}"
            tvBmiResult.text = "BMI\n${"%.2f".format(bmi)}"
        }
    }
}
