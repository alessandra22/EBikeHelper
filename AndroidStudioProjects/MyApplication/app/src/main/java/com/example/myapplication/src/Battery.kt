package com.example.myapplication.src

import java.io.Serializable
import kotlin.math.roundToInt

class Battery(
    val Ah: Double,
    val V: Int,
    controller: Double = 1.0,
    val name: String = ""
): Serializable {
    companion object {
        val battery_12_36 = Battery(12.0,36)
        val battery_13_36 = Battery(13.0,36)
        val battery_13_4_36 = Battery(13.4,36)
        val battery_14_36 = Battery(14.0,36)
        val battery_15_36 = Battery(15.0,36)
        val battery_18_36 = Battery(18.0,36)
    }

    var charge = Ah*controller
    val Wh = ((Ah * V) * 100.0).roundToInt() /100.0

    fun discharge(pow: Double, t: Double) {
        val cRate = pow / V
        charge -= cRate * (t / 60.0)
    }

    fun align(controller: Double){
        charge = Ah*controller
    }

    fun capacity(): Double{
        return ((charge/Ah)*100.0).roundToInt()/100.0
    }
}