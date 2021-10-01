package com.example.myapplication.src

import kotlin.math.pow

class Section (
    val tour: Tour,
    var togo: Double,
    val slope: Double = 0.0, // pendenza
    val Rc: Double = 0.005,
    val Cd: Double = 0.80,
    val rho: Double = 1.2
){
    val mass = tour.bike.mass + tour.biker.mass
    val area = tour.biker.area

    fun updatelen(spaceCovered: Double){
        togo -= spaceCovered
    }

    fun power(speedIn: Double, gear: Int = 0): Double {

        fun powerDrag(speed: Double): Double {
            return Cd * area * rho / 2.0 * speed.pow(3)
        }

        fun powerResist(speed: Double): Double {
            return (Costanti.g * mass * Rc * speed)
        }

        fun powerSlope(speed: Double): Double {
            return (Costanti.g * mass * slope * speed) / 100.0
        }

        if (slope < 0)
            return 0.0
        val speed: Double = if (gear == 0)
            speedIn * Costanti.cv
        else
            (speedIn / 60) * tour.bike.RollOut(gear)

        return powerDrag(speed) + powerSlope(speed) + powerResist(speed)
    }
}