package com.example.myapplication.src.altitudine

import org.osmdroid.util.GeoPoint
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OpenTopoData: Altitudine() {

    override fun getAltitudine(geoPoint: GeoPoint): Double{

        val splitGeo = geoPoint.toString().split(",")
        val lat = splitGeo[0]
        val lon = splitGeo[1]

        try{
            Thread.sleep(1000)
            val mURL = URL("https://api.opentopodata.org/v1/srtm90m?locations=$lat,$lon")

            with(mURL.openConnection() as HttpURLConnection) {
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()

                    val splitResponse = response.toString().split(",")
                    val splitH = splitResponse[1].split(":")
                    val h = splitH[1]
                    return h.toDouble()
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
            return 999999999.9
        }
    }
}