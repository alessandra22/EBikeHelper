package com.example.myapplication.src

import java.io.Serializable

class Engine(
    val torque: Double = 0.0,
    val assist: ArrayList<AssistLevels>,
    val power: Int = 250,
    val maxA: Int = 15,
    val V: Int = 36,
    val name: String = ""
): Serializable {

    companion object {
        val yamahapwse = Engine(70.0, AssistLevels.lev1)
    }

    val peakpower = maxA * V
}