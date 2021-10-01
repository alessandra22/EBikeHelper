package com.example.myapplication.ui.ciclista_fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.src.MyFileManager
import java.io.File

class CiclistaFragment : Fragment() {

    private lateinit var myContext: Context
    private lateinit var myFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ciclista, container, false)
    }

    private fun setSforzo(posizione: Int): Array<Int> {
        return when (posizione) {
            1 -> arrayOf(15, 90)
            2 -> arrayOf(20, 100)
            3 -> arrayOf(25, 105)
            4 -> arrayOf(27, 108)
            else -> arrayOf(0, 0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val massa = view.findViewById<EditText>(R.id.massaCiclistaET)
        val altezza = view.findViewById<EditText>(R.id.altezzaCiclistaET)
        val maxTorque = view.findViewById<EditText>(R.id.maxTorqueET)
        val maxCadence = view.findViewById<EditText>(R.id.maxCadenceET)
        val error = view.findViewById<TextView>(R.id.errorCiclista)
        val sforzo = view.findViewById<Spinner>(R.id.preferenzaSforzo)
        val ciclista = view.findViewById<Spinner>(R.id.selectCiclista)

        val salva = view.findViewById<Button>(R.id.salvaCiclista)
        val reset = view.findViewById<Button>(R.id.resetCiclista)

        massa.setText(""); altezza.setText(""); maxTorque.setText("")
        maxCadence.setText(""); sforzo.setSelection(1)

        myContext = (activity as MainActivity)
        myFile = (activity as MainActivity).fileBiker

        // if present, load content
        val contents = MyFileManager.load(myFile)

        if (contents != null) {
            massa.setText(contents[0])
            altezza.setText(contents[1])
            maxTorque.setText(contents[2])
            maxCadence.setText(contents[3])
            sforzo.setSelection(contents[4].toInt())
        }

        salva.setOnClickListener {
            val massaText = massa.text.toString()
            val altezzaText = altezza.text.toString()
            val maxTorqueText = maxTorque.text.toString()
            val maxCadenceText = maxCadence.text.toString()

            if (altezzaText != "" && massaText != "") {
                val sel = sforzo.selectedItemPosition
                if (ciclista.selectedItemPosition == 0) {
                    if (maxTorqueText != "" && maxCadenceText != "")
                        MyFileManager.save(
                            myContext,
                            myFile,
                            "$massaText,$altezzaText,$maxTorqueText,$maxCadenceText,$sel".toByteArray()
                        ) else
                        error.text = getString(R.string.error_fields_empty)
                } else {
                    val ret = setSforzo(ciclista.selectedItemPosition)
                    val mt = ret[0]
                    val mc = ret[1]
                    MyFileManager.save(
                        myContext,
                        myFile,
                        "$massaText,$altezzaText,$mt,$mc,$sel".toByteArray()
                    )
                }
            } else
                error.text = getString(R.string.error_fields_empty)

        }

        reset.setOnClickListener {

            val builder = AlertDialog.Builder(context)

            builder.setCancelable(true)
            builder.setTitle("Attenzione")
            builder.setMessage("Vuoi cancellare tutti questi dati?")

            builder.setPositiveButton("SI'") { dialog, which ->
                massa.setText(""); altezza.setText(""); maxTorque.setText("")
                maxCadence.setText(""); sforzo.setSelection(1); ciclista.setSelection(0)

                MyFileManager.delete(myFile)
                dialog.dismiss()
            }

            builder.setNegativeButton(
                "NO"
            ) { dialog, _ -> // Do nothing
                dialog.dismiss()
            }

            builder.show()
        }
    }
}