package com.example.myapplication.ui.livelli

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.src.MyFileManager
import java.io.File

class LivelliFragment : Fragment() {

    private lateinit var myContext: Context
    private lateinit var myFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_livelli, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val aggiorna: Button = view.findViewById(R.id.aggiornaBT)
        val salvaLivelli: Button = view.findViewById(R.id.salvaLivelliBT)
        val nLivelliET: EditText = view.findViewById(R.id.nLivelliET)
        val erroreLivelli: TextView = view.findViewById(R.id.erroreLivelliTV)

        myContext = (activity as MainActivity)
        myFile = (activity as MainActivity).fileLivelli

        val textViews = ArrayList<TextView>()
        val editTexts = ArrayList<EditText>()

        val l1ET: EditText = view.findViewById(R.id.livello1ET)
        val l2TV: TextView = view.findViewById(R.id.livello2TV)
        val l2ET: EditText = view.findViewById(R.id.livello2ET)
        val l3TV: TextView = view.findViewById(R.id.livello3TV)
        val l3ET: EditText = view.findViewById(R.id.livello3ET)
        val l4TV: TextView = view.findViewById(R.id.livello4TV)
        val l4ET: EditText = view.findViewById(R.id.livello4ET)
        val l5TV: TextView = view.findViewById(R.id.livello5TV)
        val l5ET: EditText = view.findViewById(R.id.livello5ET)
        val l6TV: TextView = view.findViewById(R.id.livello6TV)
        val l6ET: EditText = view.findViewById(R.id.livello6ET)

        textViews.add(l2TV); textViews.add(l3TV); textViews.add(l4TV)   // textViews[0] = l2TV
        textViews.add(l5TV); textViews.add(l6TV)                        // textViews[4] = l6TV

        editTexts.add(nLivelliET)                                       // editTexts[0] = nLivelli
        editTexts.add(l1ET); editTexts.add(l2ET); editTexts.add(l3ET)   // editTexts[1] = l1ET
        editTexts.add(l4ET); editTexts.add(l5ET); editTexts.add(l6ET)   // editTexts[6] = l6ET


        val contents = MyFileManager.load(myFile)
        if(contents != null)
            MyFragmentManager.setContent(contents, textViews, editTexts)

        MyFragmentManager.setOnClickListeners(editTexts)

        aggiorna.setOnClickListener {
            MyFragmentManager.setAggiornaListener(myContext, textViews, editTexts, erroreLivelli)
        }

        salvaLivelli.setOnClickListener {
            val toWrite = MyFragmentManager.setToWrite(editTexts)
            if (toWrite != null)
                MyFileManager.save(myContext, myFile, toWrite.toByteArray())
            else
                erroreLivelli.text = myContext.getString(R.string.error_fields_empty)
        }
    }
}