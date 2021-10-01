package com.example.myapplication

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.myapplication.src.*
import com.example.myapplication.src.altitudine.OpenTopoData
import org.osmdroid.bonuspack.routing.Road
import java.io.File
import kotlin.math.roundToInt

class IstruzioniActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_istruzioni)

        // Recupero Bike e Biker salvati da file
        val fileBiker = File(this.filesDir, MainActivity.savingBiker)
        val fileBike = File(this.filesDir, MainActivity.savingBike)
        val fileLivelli = File(this.filesDir, MainActivity.savingLivelli)

        val bike = MyFileManager.setBike(fileBike, fileLivelli)
        val biker = MyFileManager.setBiker(fileBiker)

        println("Carica iniziale = " + bike.battery.charge)

        // Recupero road creata in MappaActivity
        val c = intent.getParcelableExtra<Road>("EXTRA_ROAD")

        val list = findViewById<ListView>(R.id.list_view) // Lista in cui mettere i risultati
        val toWrite = ArrayList<String>()   // dove salvare i risultati per metterli nella lista

        if (c != null) {
            val otd = OpenTopoData()        // CAMBIA QUI PER CAMBIARE API!!

            val directions = ArrayList<Direction>()
            var nextPartenza =
                otd.getAltitudine(c.mNodes[0].mLocation) // La partenza di i+1 è l'arrivo di i

            for (i in 0 until c.mNodes.size - 1) {
                val altitudinePartenza = nextPartenza
                val altitudineArrivo = otd.getAltitudine(c.mNodes[i + 1].mLocation)
                nextPartenza = altitudineArrivo

                val lunghezza = c.mNodes[i].mLength * 1000 //in chilometri

                val direction = Direction(altitudinePartenza, altitudineArrivo, lunghezza)
                directions.add(direction)

                // toWrite.add("DIREZIONE " + (i+1) + ": " + direction.altitudinePartenza.toString() + " " + direction.altitudineArrivo + " " + direction.lunghezza)
            }

            val tour = Tour(bike, biker, directions)
            var error = false

            for (j in 0 until tour.plan.size) {     // charge : Ah = x : 100
                val tt = tour.plan[j]
                println(c.mNodes[j].mDuration / 60)
                bike.battery.discharge(tt.powEngine, c.mNodes[j].mDuration / 60)
                val batteria =
                    (((bike.battery.charge * 100) / bike.battery.Ah) * 100).roundToInt() / 100.0
                toWrite.add(
                    (j + 1).toString() + ": velocita' = " + tt.speed +
                            "\nlunghezza = " + c.mNodes[j].mLength + "km" +
                            "\nlivello di assistenza = " + tt.alevName +
                            "\ncambio = " + tt.sh +
                            "\n% batteria prevista = $batteria"
                )

                if (tt.alevName == "IMPOSSIBILE")
                    error = true
            }

            if (error) {
                MyFileManager.showDisclaimer(
                    this,
                    "Attenzione!",
                    "Il percorso calcolato necessita di più forza di quella offerta da bicicletta + ciclista"
                )
            }

            if (bike.battery.charge < 0) {
                MyFileManager.showDisclaimer(
                    this,
                    "Attenzione!",
                    "Il percorso calcolato richiede più energia di quella disponibile nella batteria!"
                )
            }

        }
        val arrayAdapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, toWrite)
        list.adapter = arrayAdapter
    }
}


/*  PER ISTRUZIONI (CHE POSSONO ESSERE NULL)

        if (c != null) {
            for(i in c.mNodes.indices){
                val geoPoint = c.mNodes[i].mLocation
                val h = geoPoint.altitude
                if(c.mNodes[i].mInstructions != null)
                    toWrite.add(c.mNodes[i].mInstructions.toString())
                else
                    toWrite.add((i+1).toString())
            }
        }
*/

/*  PER PROVARE CON UN TOUR GIA' PRONTO

    fun creaTour(dir: ArrayList<Direction>): Tour {
        val yamahapwse = Engine( torque = 70.0, AssistLevels.createAssistLevels(arrayOf("Eco+", "Eco", "Std", "Hard"), arrayOf(0.5, 1.0, 1.9, 2.8) ))
        val intube500 = Battery(13.4, 36)
        val winoraY9_2019 = Bike(25,28.0,1.75,0.1725,38.0,gears = Cassette.Shimano11_32,engine = yamahapwse,battery = intube500,name = "winoraY9_2019")
        val mauro = Biker(72, 175, maxTorque = 15, maxCadence = 70, preference = Costanti.normal)

        return Tour(winoraY9_2019, mauro, dir)
    }
 */

/*  STAMPA INFORMAZIONI

    toWrite.add((j + 1).toString() + ": " + tt.powEngine + " " + tt.speed + " " + tt.alevName + " " + tt.sh + " " + tt.tq + " " + tt.powBiker + " " + tt.cd)

 */