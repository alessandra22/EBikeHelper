package com.example.myapplication.src

import java.io.Serializable

class Direction (
    val altitudinePartenza: Double,
    val altitudineArrivo: Double,
    val lunghezza: Double,
    val terreno: Double = Costanti.asphalt_good
): Serializable{
    companion object {
        fun createDirections(): ArrayList<Direction> {
            val d1 = Direction(10.0, 10.0, 300.0, Costanti.asphalt_good)
            val d2 = Direction(10.0, 50.0, 750.0, Costanti.asphalt_good)
            val d3 = Direction(50.0, 120.0, 1200.0, Costanti.asphalt_good)
            val d4 = Direction(120.0, 500.0, 7800.0, Costanti.asphalt_good)
            val d5 = Direction(500.0, 5.0, 12000.0, Costanti.asphalt_good)

            val ret = ArrayList<Direction>()
            ret.add(d1); ret.add(d2); ret.add(d3); ret.add(d4); ret.add(d5);

            return ret
        }

        val directions = createDirections()

    }
}