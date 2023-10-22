package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var isOperator = false
    private var hasOperator = false
    private var currentOperand = 0

    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.txt_expression)
    }
    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.txt_result)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.btn_00 -> numberButtonClicked("")
            R.id.btn_0 -> numberButtonClicked("0")
            R.id.btn_1 -> numberButtonClicked("1")
            R.id.btn_2 -> numberButtonClicked("2")
            R.id.btn_3 -> numberButtonClicked("3")
            R.id.btn_4 -> numberButtonClicked("4")
            R.id.btn_5 -> numberButtonClicked("5")
            R.id.btn_6 -> numberButtonClicked("6")
            R.id.btn_7 -> numberButtonClicked("7")
            R.id.btn_8 -> numberButtonClicked("8")
            R.id.btn_9 -> numberButtonClicked("9")

            R.id.btn_plus -> operatorButtonClicked("+")
            R.id.btn_minus -> operatorButtonClicked("-")
            R.id.btn_multi -> operatorButtonClicked("X")
            R.id.btn_div -> operatorButtonClicked("/")
            R.id.btn_mod -> operatorButtonClicked("%")
        }
    }


    private fun numberButtonClicked(number: String) {
        if (isOperator) {
            expressionTextView.append(" ")
        }
        isOperator = false

        val expressionText = expressionTextView.text.split(" ")

        expressionTextView.append(number)
        resultTextView.text = calculateExpression()
    }

    private fun operatorButtonClicked(operator: String) {
        if (expressionTextView.text.isEmpty()) {
            return
        }

        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "Một toán tử chỉ có thể được sử dụng một lần.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                expressionTextView.append(" $operator")
            }

        }
        val ssb = SpannableStringBuilder(expressionTextView.text)

        expressionTextView.text = ssb
        isOperator = true
        hasOperator = true
    }

    fun resultButtonClicked(v: View) {
        val expressionTexts = expressionTextView.text.split(" ")
        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }
        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "Hãy hoàn thành công thức", Toast.LENGTH_SHORT).show()
            return
        }
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "Đã xảy ra lỗi.", Toast.LENGTH_SHORT).show()
            return
        }
        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        resultTextView.text =""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false

    }


    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }


        val op = expressionTexts[1]
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()

            return when (op) {
                "+" -> (exp1 + exp2).toString()
                "-" -> (exp1 - exp2).toString()
                "X" -> (exp1 * exp2).toString()
                "%" -> (exp1 % exp2).toString()
                "/" -> (exp1 / exp2).toString()
                else -> ""
            }
    }
    fun dotButtonClicked(v: View){
        val expressionTexts = expressionTextView.text.split(" ")

            if(hasOperator != true) {
                if (expressionTextView.text.length == 0) {
                    expressionTextView.append("0.")
                } else if (!expressionTextView.text.toString().contains(".")) {
                    expressionTextView.append(".")
                }
            }
            else{
                if (expressionTexts.size == 2) {
                    expressionTextView.append("0.")
                } else if (expressionTexts.size == 3 && !expressionTexts[2].toString().contains(".")) {
                    expressionTextView.append(".")
                }
            }
    }
    fun clearButtonClicked(v: View) {
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator =false
    }
    fun backButtonClicked(v: View){
        expressionTextView.setText(expressionTextView.text.dropLast(1))
        isOperator = false
        hasOperator =false
    }

    fun ClearEntryButtonClicked(v: View)
    {
        val expressionTexts = expressionTextView.text.split(" ")

        if (expressionTexts.size == 3) {
            val exp = expressionTexts[0].toBigInteger()
            val op = expressionTexts[1]
            expressionTextView.text = exp.toString() + " " + op + " "
        }
        else if (expressionTexts.size == 1) {
            expressionTextView.text = ""
        }
    }

}

fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}