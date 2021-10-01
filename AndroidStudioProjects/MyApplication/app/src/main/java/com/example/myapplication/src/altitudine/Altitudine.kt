package com.example.myapplication.src.altitudine

import org.osmdroid.util.GeoPoint

abstract class Altitudine {
    abstract fun getAltitudine(geoPoint: GeoPoint): Double
}