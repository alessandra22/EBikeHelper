package com.example.myapplication.ui.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.src.*
import java.io.File


class HomeFragment : Fragment() {
    private lateinit var myContext: Context
    private lateinit var fileBiker: File
    private lateinit var fileBike: File
    private lateinit var fileLivelli: File

    private val debug = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myContext = (activity as MainActivity)
        fileBiker = (activity as MainActivity).fileBiker
        fileBike = (activity as MainActivity).fileBike
        fileLivelli = (activity as MainActivity).fileLivelli
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /*
        private fun deleteFiles() {  // for debugging
            MyFileManager.delete(fileBiker)
            MyFileManager.delete(fileBike)
            MyFileManager.delete(fileLivelli)
        }
    */
    private fun setErrors(view: View, bike: Bike, biker: Biker) {
        val errorBikerET = view.findViewById<TextView>(R.id.errorBiker)
        val errorBikeET = view.findViewById<TextView>(R.id.errorBike)
        val errorLivelliET = view.findViewById<TextView>(R.id.errorLivelli)

        if (debug) {
            val contentBiker = "File Biker: " + MyFileManager.load(fileBiker)?.joinToString()
            val contentBike = "File Bike: " + MyFileManager.load(fileBike)?.joinToString()
            val contentLevels = "File Livelli: " + MyFileManager.load(fileLivelli)?.joinToString()

            errorBikerET.text = contentBiker
            errorBikeET.text = contentBike
            errorLivelliET.text = contentLevels
        }

        if (bike.mass != 0)
            if (biker.mass != 0)
                view.findViewById<TextView>(R.id.tuttoOk).text = getString(R.string.no_errors)
            else
                view.findViewById<TextView>(R.id.tuttoOk).text = getString(R.string.biker_error)
        else
            view.findViewById<TextView>(R.id.tuttoOk).text = getString(R.string.bike_error)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MyFileManager.showDisclaimer(
            myContext,
            "Il percorso calcolato non può tenere conto di fattori esterni",
            "Il tipo di terreno pre-impostato è: asfalto in medie condizioni." +
                    "\nIl vento è considerato: assente. " +
                    "\n\nTieni queste informazioni a mente mentre prepari il tuo percorso!"
        )

        val bike = MyFileManager.setBike(fileBike, fileLivelli)
        val biker = MyFileManager.setBiker(fileBiker)
        setErrors(view, bike, biker)
    }
}