package com.example.myapplication.src

import android.app.AlertDialog
import android.content.Context
import com.example.myapplication.ui.bici.MyDataCreator
import java.io.File
import android.content.DialogInterface


class MyFileManager {
    companion object {
        fun showDisclaimer(context: Context, titolo: String, testo: String){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setTitle(titolo)
            builder.setMessage(testo)
            builder.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.cancel() }
            builder.show()
        }

        fun save(context: Context, file: File, toWrite: ByteArray) {
            delete(file)
            context.openFileOutput(file.name, 0).use {
                it.write(toWrite)
            }
            if (file.exists())
                println("File salvato correttamente")
            else
                println("Errore nella creazione del file")
        }

        fun delete(file: File) {
            file.delete()
            if (file.exists())
                println("Errore nell'eliminazione del file")
            else
                println("File eliminato correttamente")
        }

        fun load(file: File): List<String>? {
            return if (file.exists())
                file.readText().split(",")
            else
                null
        }

        fun setBiker(fileBiker: File): Biker {
            val contents = load(fileBiker)
            return if (contents != null && contents.size == 5) {
                var preferenza = 0.0
                when (contents[4]) {
                    "0" -> preferenza = 1.9
                    "1" -> preferenza = 2.1
                    "2" -> preferenza = 10.0
                }
                Biker(
                    mass = contents[0].toInt(),
                    altezza = contents[1].toInt(),
                    maxTorque = contents[2].toInt(),
                    maxCadence = contents[3].toInt(),
                    preference = preferenza
                )
            } else
                Biker(0, 0, 0, 0, 0.0)
        }

        fun setBike(fileBike: File, fileLivelli: File): Bike {
            val contents = load(fileBike)
            if (contents != null) { //&& (contents.size == 2 || contents.size == 9)
                if (contents[0] == "B")
                    when (contents[1]) { // ADD HERE NEW E-BIKES
                        "1" -> return Bike.winoraY9_2019
                    }
                if (contents[0] == "C") {

                    return Bike(
                        mass = contents[1].toInt(),
                        tyre0 = contents[3].toDouble(),
                        tyre1 = contents[4].toDouble(),
                        0.0,
                        crank = contents[5].toDouble(),
                        gears = MyDataCreator.setCassette(contents[9].toInt()),
                        engine = Engine(
                            assist = MyDataCreator.setLivelli(contents[10].toInt(), fileLivelli),
                            maxA = contents[7].toInt(),
                            V = contents[8].toInt()
                        ),
                        battery = Battery(contents[5].toDouble(), contents[6].toInt())
                    )
                }
            }

            return Bike(
                0,
                0.0,
                0.0,
                0.0,
                0.0,
                Cassette.Shimano11_32,
                Engine.yamahapwse,
                Battery.battery_13_4_36,
                "Errore"
            )
        }
    }
}