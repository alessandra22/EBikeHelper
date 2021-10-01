package com.example.myapplication.src

import java.io.Serializable
import java.lang.Math.PI
import kotlin.math.roundToInt

class Bike(
    val mass: Int,
    val tyre0: Double,
    val tyre1: Double,
    val crankArm: Double,
    val crank: Double,
    val gears: Cassette,
    val engine: Engine,
    val battery: Battery,
    val name: String = ""
) : Serializable {

    private val circ = PI * tyre0 * 0.0254  // in metri
    val width = tyre1 * 2.54      // in centimetri

    override fun toString(): String {
        return "mass: $mass\ncirc: $circ\nwidth: $width\ncrank: $crank"
    }

    companion object {
        val winoraY9_2019 = Bike(
            25, 28.0, 1.75, 0.1725, 38.0,
            gears = Cassette.Shimano11_32,
            engine = Engine.yamahapwse,
            battery = Battery.battery_13_4_36,
            name = "winoraY9_2019"
        )
    }

    fun RollOut(gear: Int): Double {
        for (i in 0 until gears.gears.size) {
            if (gears.gears[i].gear == gear)
                return circ * (crank / gears.gears[i].value.toDouble())

        }
        return 0.0
    }

    fun cadence(speed: Double, shift: Int): Double {
        return ((speed * 1000 * 100 / (60 * RollOut(shift))).roundToInt() / 100.0)
    }

    fun speed(cadence: Double, shift: Int): Double {
        return (((cadence / 60) * RollOut(shift) * 100.0) / Costanti.cv).roundToInt() / 100.0
    }
}