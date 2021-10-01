package com.example.myapplication.src

import java.io.Serializable

class AssistLevels (
    val name: String,
    val value: Double): Serializable{

    companion object{
        fun createAssistLevels(nomi: Array<String>, valori: Array<Double>): ArrayList<AssistLevels>{
            val ret = ArrayList<AssistLevels>()
            for (i in nomi.indices){
                val al = AssistLevels(nomi[i], valori[i])
                ret.add(al)
            }
            return ret
        }

        private val eco = arrayOf("Eco+", "Eco", "Std", "Hard")

        val lev1 = createAssistLevels(eco, arrayOf(0.5, 1.0, 1.9, 3.0))

        val lev2 = createAssistLevels(eco, arrayOf(0.4, 1.0, 1.5, 3.0))

        val lev3 = createAssistLevels(eco, arrayOf(0.5, 1.0, 1.5, 3.0))

        val lev4 = createAssistLevels(eco, arrayOf(0.4, 1.0, 1.7, 3.0))

        val lev5 = createAssistLevels(eco, arrayOf(0.5, 1.0, 1.7, 3.0))
    }
}