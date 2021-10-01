package com.example.myapplication.src

class Costanti{

    companion object {
        val g = 9.80665    // m/s^2
        val cv = 5.0 / 18    // Fattore di conversione Km/h-->m/s
        val maxspeed = 25.0 // Maximum speed with engine assistance

        val battery = 10.0   // While planning section, gives priority to save battery
        val normal = 2.1   // Seek a tradeoff between saving battery and effort
        val effort = 1.9   // Require strong help from the engine

        val concrete = 0.002
        val asphalt_good = 0.006
        val asphalt_low = 0.004      // Low severity crack
        val asphalt_moderate = 0.005 // Moderate severity crack
        val asphalt_high = 0.006     // High severity crack
        val rough_paved = 0.008
        val gravel = 0.008
        val grass = 0.009
        val sand = 0.036

        // If the engine has an odd number of assistance levels, relax and
        // normal coincide
    }
}
