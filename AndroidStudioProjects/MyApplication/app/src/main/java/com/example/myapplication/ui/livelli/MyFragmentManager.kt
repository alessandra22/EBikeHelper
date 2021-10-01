package com.example.myapplication.ui.livelli

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.src.MyFileManager
import java.lang.Exception

class MyFragmentManager {
    companion object {
        fun setContent( //ES FILE: 5,0.3,1.0,1.9,2.5,3.0  (Primo numero = nLivelli)
            content: List<String>,
            textViews: ArrayList<TextView>,
            editTexts: ArrayList<EditText>
        ) {
            editTexts[0].setText(content[0]) // n livelli
            editTexts[1].setText(content[1]) // livello 1 (non serve nulla sulla TV)
            for (i in 2 until content[0].toInt()+1) {
                textViews[i-2].visibility = View.VISIBLE
                editTexts[i].visibility = View.VISIBLE
                editTexts[i].setText(content[i])
            }
        }

        fun setOnClickListeners(editTexts: ArrayList<EditText>) {
            for (i in 0 until editTexts.size)
                editTexts[i].setOnClickListener {
                    editTexts[i].selectAll()
                }
        }

        fun setAggiornaListener(
            context: Context,
            textViews: ArrayList<TextView>,
            editTexts: ArrayList<EditText>,
            errore: TextView
        ) {
            try {
                editTexts[0].text.toString().toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                errore.text = context.getString(R.string.number_not_valid)
                return
            }
            when {
                editTexts[0].text.toString().toInt() < 1 -> errore.text =
                    context.getString(R.string.numero_troppo_piccolo)
                editTexts[0].text.toString().toInt() > 6 -> errore.text =
                    context.getString(R.string.numero_troppo_grande)
                else -> {
                    for (i in 2 until editTexts.size) {
                        textViews[i-2].visibility = View.GONE
                        editTexts[i].visibility = View.GONE
                        editTexts[i].setText("")
                    }
                    for (i in 2 until editTexts[0].text.toString().toInt() + 1) {
                        textViews[i-2].visibility = View.VISIBLE
                        editTexts[i].visibility = View.VISIBLE
                    }
                }
            }
        }

        fun setToWrite(editTexts: ArrayList<EditText>): String? {
            var nLivelli = -1
            try {
                nLivelli = editTexts[0].text.toString().toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            var error = false
            for (i in 1 until nLivelli+1) {
                if (editTexts[i].text.toString().isEmpty())
                    error = true
            }

            return if (!error && nLivelli != -1) {
                var l = editTexts[1].text.toString()
                var toWrite = "$nLivelli,$l"

                for (i in 2 until nLivelli+1) {
                    l = editTexts[i].text.toString()
                    toWrite += ",$l"
                }
                toWrite
            } else {
                null
            }
        }
    }
}
