package com.example.myapplication.src

import kotlinx.parcelize.Parcelize
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.roundToInt

class Tour (
    val bike: Bike,
    val biker: Biker,
    val directions: ArrayList<Direction> = Direction.createDirections(),
    ) {

    val sections = creaSezioni()
    var cur = 0
    val plan = creaPlan() // Arraylist di Informazione

    fun creaSezioni(): ArrayList<Section>{
        val ret = ArrayList<Section>()
        for (i in 0 until directions.size) {
            val s = Section(
                this,
                directions[i].lunghezza,
                slope = 100 * (directions[i].altitudineArrivo - directions[i].altitudinePartenza) / directions[i].lunghezza,
                Rc = directions[i].terreno
            )
            ret.add(s)
        }
        return ret
    }

    fun planner(section: Section): Informazione{
        fun torque(power: Double, speed: Double, shift: Int): Array<Double>{
            val ret = speed * Costanti.cv / bike.RollOut(shift)*60
            return arrayOf(( power/ (ret*PI/30) ), ret.roundToInt().toDouble() )
        }
        fun removeLower(levels: ArrayList<AssistLevels>, d: Double): ArrayList<AssistLevels>{
            val ret = ArrayList<AssistLevels>()
            for(i in 0 until levels.size){
                if(i >= d)
                    ret.add(levels[i])
            }
            return ret
        }

        fun min(a: Double, b: Double): Double{
            return if(a<b)
                a
            else
                b
        }

        val shifts = bike.gears.gears
        val alevs = removeLower(bike.engine.assist, floor((bike.engine.assist.size).toDouble()/biker.preference))
        val h = shifts[shifts.size - 1].gear

        for (i in 0 until alevs.size){
            var speed = Costanti.maxspeed

            while(speed>=10){
                val power = section.power(speed)
                val powEngine = min(power*alevs[i].value / (1 + alevs[i].value), bike.engine.peakpower.toDouble())
                val powBiker = power - powEngine

                var sh = h

                while(sh>0){
                    val ret = torque(powBiker, speed, sh)
                    val tq = (ret[0]*100.0).roundToInt().toDouble() / 100.0
                    val cd = ret[1]

                    when {
                        cd > biker.maxCadence -> sh = 0
                        tq > biker.maxTorque -> sh--
                        else -> return Informazione((powEngine*100.0).roundToInt()/100.0, speed, alevs[i].name, sh, tq, (powBiker*100.0).roundToInt()/100.0, cd)
                    }
                }
                speed--
            }
        }
        println(section.slope)
        return Informazione(0.0, 0.0, "IMPOSSIBILE", 0, 0.0, 0.0, 0.0)
    }

    fun creaPlan(): ArrayList<Informazione>{
        val ret = ArrayList<Informazione>()
        for(i in 0 until sections.size){
            val info = planner(sections[i])
            ret.add(info)
        }
        return ret
    }
}