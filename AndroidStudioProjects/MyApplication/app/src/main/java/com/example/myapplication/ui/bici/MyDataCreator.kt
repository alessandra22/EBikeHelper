package com.example.myapplication.ui.bici

import android.widget.EditText
import com.example.myapplication.src.*
import java.io.File

class MyDataCreator {
    companion object {
        fun setBattery(position: Int, ah: EditText, V: EditText): Battery {
            when (position) {
                0 -> return Battery(ah.text.toString().toDouble(), V.text.toString().toInt())
                1 -> return Battery.battery_12_36
                2 -> return Battery.battery_13_36
                3 -> return Battery.battery_13_4_36
                4 -> return Battery.battery_14_36
                5 -> return Battery.battery_15_36
                6 -> return Battery.battery_18_36
            }
            return Battery(0.0, 0)
        }

        fun setCassette(position: Int): Cassette {
            when (position) {
                0 -> return Cassette.Shimano11_32
                1 -> return Cassette.Shimano11_40
                2 -> return Cassette.Shimano11_42
                3 -> return Cassette.Shimano11_46
                4 -> return Cassette.Shimano10_45
                5 -> return Cassette.Shimano10_51
                6 -> return Cassette.Sram10_42
                7 -> return Cassette.Sram10_46
                8 -> return Cassette.Sram10_52
            }
            return Cassette(intArrayOf(0))
        }

        fun setLivelli(position: Int, fileLivelli: File): ArrayList<AssistLevels> {
            var levels = ArrayList<AssistLevels>()
            when (position) {
                0 -> {  // leggi da file
                    val content = MyFileManager.load(fileLivelli)
                    if (content != null)
                        for (i in 1 until content[0].toInt()+1)
                            levels.add(AssistLevels("lev$i", content[i].toDouble()))
                }
                1 -> levels = AssistLevels.lev1
                2 -> levels = AssistLevels.lev2
                3 -> levels = AssistLevels.lev3
                4 -> levels = AssistLevels.lev4
                5 -> levels = AssistLevels.lev5
            }
            return levels
        }

        fun setMotore(
            position: Int,
            levels: ArrayList<AssistLevels>,
            maxA: EditText,
            V: EditText
        ): Engine {
            var motore = Engine(assist = levels, maxA = 0, V = 0)

            when (position) {
                0 -> motore = Engine(
                    assist = levels,
                    maxA = maxA.text.toString().toInt(),
                    V = V.text.toString().toInt()
                )
                1 -> motore = Engine.yamahapwse
            }

            return motore
        }

        fun getPositionBatteria(
            ah: String,
            V: String
        ): Int {  // return spinner position, if present, else -1
            if (V.toInt() == 36)
                when (ah.toDouble()) {
                    12.0 -> return 1
                    13.0 -> return 2
                    13.4 -> return 3
                    14.0 -> return 4
                    15.0 -> return 5
                    18.0 -> return 6
                }
            return -1
        }

        fun getPositionEngine(maxA: String, V: String): Int {
            if (maxA.toInt() == 15 && V.toInt() == 36)
                return 1

            return -1
        }


    }
}