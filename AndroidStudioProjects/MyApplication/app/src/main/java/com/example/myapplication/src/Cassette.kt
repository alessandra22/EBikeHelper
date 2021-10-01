package com.example.myapplication.src

import java.io.Serializable

class Cassette (
    values: IntArray,
    val name: String = ""): Serializable {

    class Gear(
        val gear: Int,
        val value: Int,
    ): Serializable

    val gears = createCassette(values)

    companion object{
        fun createCassette(values: IntArray): ArrayList<Gear>{
            val ret = ArrayList<Gear>(values.size)
            for(i in values.indices){
                val g = Gear(i+1, values[i])
                ret.add(g)
            }
            return ret
        }

        val Shimano11_32 = Cassette(intArrayOf(34,30,26,23,20,17,15,13,11))
        val Shimano11_40 = Cassette(intArrayOf(40,35,31,27,24,21,19,17,15,13,11))
        val Shimano11_42 = Cassette(intArrayOf(42,37,32,28,24,21,19,17,15,13,11))
        val Shimano11_46 = Cassette(intArrayOf(46,37,32,28,24,21,19,17,15,13,11))

        val Shimano10_45 = Cassette(intArrayOf(45,40,36,32,28,24,21,18,16,14,12,10))
        val Shimano10_51 = Cassette(intArrayOf(51,45,39,33,28,24,21,18,16,14,12,10))

        val Sram10_42 = Cassette(intArrayOf(42,36,32,28,24,21,18,16,14,12,10))
        val Sram10_46 = Cassette(intArrayOf(46,39,33,28,24,21,18,16,14,12,10))
        val Sram10_52 = Cassette(intArrayOf(52,42,36,32,28,24,21,18,16,14,12,10))

    }
}