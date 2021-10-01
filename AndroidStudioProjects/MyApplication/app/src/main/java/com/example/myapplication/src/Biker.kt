package com.example.myapplication.src

import java.io.Serializable
import kotlin.math.roundToInt

class Biker(
    val mass: Int,  //chilogrammi
    altezza: Int,   //centimetri
    val maxTorque: Int = 15,
    val maxCadence: Int = 80,
    val preference: Double = Costanti.battery
) : Serializable {

    val area = ((0.005 * mass + 0.075) * 1000).roundToInt().toDouble() / 1000

    override fun toString(): String {
        return "mass: $mass\narea: $area\nmaxTorque: $maxTorque\nmaxCadence: $maxCadence\npreferences: $preference"
    }


    /*
     area = 0.005*mass + 0.075   ->   80kg = 0.475m2
                                      75kg = 0.45m2
                                      70kg = 0.425m2+    [...]

    altezza irrilevante: la crescita per altezza o peso è lineare, per ogni chilo in più oltre
    quello ideale, c'è un aumento di 0.005m2, perciò è più facile ricavare una funzione lineare
    */
}