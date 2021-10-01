package com.example.myapplication.ui.bici

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.MappaActivity
import com.example.myapplication.R
import com.example.myapplication.src.*
import java.io.File


class BiciFragment : Fragment() {
    private lateinit var myContext: Context
    private lateinit var myFile: File
    private lateinit var fileLivelli: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bici, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mass = view.findViewById<EditText>(R.id.massaBiciET)
        val tyre0 = view.findViewById<EditText>(R.id.ruota1ET)
        val tyre1 = view.findViewById<EditText>(R.id.ruota2ET)
        val crank = view.findViewById<EditText>(R.id.crankET)

        val maxA = view.findViewById<EditText>(R.id.maxAET)
        val vEngine = view.findViewById<EditText>(R.id.vEngineET)
        val ah = view.findViewById<EditText>(R.id.ahET)
        val vBattery = view.findViewById<EditText>(R.id.vBatteryET)

        val selectBici = view.findViewById<Spinner>(R.id.selectBici)
        val selectLivelli = view.findViewById<Spinner>(R.id.selectLivelli)
        val selectMotore = view.findViewById<Spinner>(R.id.selectMotore)
        val selectBatteria = view.findViewById<Spinner>(R.id.selectBatteria)
        val selectCambio = view.findViewById<Spinner>(R.id.selectCambio)

        val errore = view.findViewById<TextView>(R.id.errorBici)
        val salvaBici = view.findViewById<Button>(R.id.salvaBici)
        val salvaBici2 = view.findViewById<Button>(R.id.salvaBici2)
        val reset = view.findViewById<Button>(R.id.resetBici)

        myContext = (activity as MainActivity)
        myFile = (activity as MainActivity).fileBike
        fileLivelli = (activity as MainActivity).fileLivelli

        // LOADING CONTENT
        val contents = MyFileManager.load(myFile)

        if (contents != null) {
            println("File presente")
            if (contents[0] == "B") {
                when (contents[1]) {
                    "1" -> selectBici.setSelection(1)
                }
            } else {
                mass.setText(contents[1])
                crank.setText(contents[2])
                tyre0.setText(contents[3])
                tyre1.setText(contents[4])

                // get batteria
                var pos = MyDataCreator.getPositionBatteria(contents[5], contents[6])
                if (pos != -1)
                    selectBatteria.setSelection(pos)
                else {
                    selectBatteria.setSelection(0)
                    ah.setText(contents[5])
                    vBattery.setText(contents[6])
                }

                // get livelli
                selectLivelli.setSelection(contents[9].toInt())

                // get engine
                pos = MyDataCreator.getPositionEngine(contents[7], contents[8])
                if (pos != -1)
                    selectMotore.setSelection(pos)
                else {
                    selectBatteria.setSelection(0)
                    maxA.setText(contents[7])
                    vEngine.setText(contents[8])
                }

                // get cambio
                selectCambio.setSelection(contents[9].toInt())
            }
        } else
            println("File non presente")

        // SAVING CONTENT
        salvaBici.setOnClickListener {
            if (selectBici.selectedItem == "--non presente--")
                errore.text = getString(R.string.error_bike_not_selected)
            else {
                when (selectBici.selectedItemPosition) {
                    1 -> MyFileManager.save(myContext, myFile, ("B,1").toByteArray())
                }
            }
        }

        salvaBici2.setOnClickListener {
            val battery = MyDataCreator.setBattery(
                selectBatteria.selectedItemPosition, ah, vBattery
            )
            val livelli = MyDataCreator.setLivelli(selectLivelli.selectedItemPosition, fileLivelli)
            val motore = MyDataCreator.setMotore(
                selectMotore.selectedItemPosition,
                livelli,
                maxA,
                vEngine
            )
            val s1 = mass.text; val s2 = crank.text; val s3 = tyre0.text; val s4 = tyre1.text;
            val s5 = battery.Ah; val s6 = battery.V
            val s7 = motore.maxA; val s8 = motore.V
            val s9 = selectCambio.selectedItemPosition.toString()
            val s10 = selectLivelli.selectedItemPosition.toString()

            val toWrite = "C,$s1,$s2,$s3,$s4,$s5,$s6,$s7,$s8,$s9,$s10".toByteArray()

            MyFileManager.save(myContext, myFile, toWrite)
        }

        // DELETING CONTENT
        reset.setOnClickListener {

            val builder = AlertDialog.Builder(context)

            builder.setCancelable(true)
            builder.setTitle("Attenzione")
            builder.setMessage("Vuoi cancellare tutti questi dati?")

            builder.setPositiveButton("SI'") { dialog, which ->
                mass.setText(""); tyre0.setText(""); tyre1.setText(""); crank.setText("")
                maxA.setText(""); vEngine.setText(""); ah.setText(""); vBattery.setText("")
                selectBici.setSelection(0); selectLivelli.setSelection(0)
                selectCambio.setSelection(0); selectMotore.setSelection(0)
                selectBatteria.setSelection(0)

                MyFileManager.delete(myFile)
                dialog.dismiss()
            }

            builder.setNegativeButton(
                "NO"
            ) { dialog, which -> // Do nothing
                dialog.dismiss()
            }

            builder.show()

        }
    }
}
