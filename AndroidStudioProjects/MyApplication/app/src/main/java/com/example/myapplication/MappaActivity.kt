package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.src.MyFileManager
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Double.parseDouble
import java.net.HttpURLConnection
import java.net.URL


class MappaActivity : AppCompatActivity() {
    private var road: Road = Road()
    var lat = 100.0
    var lon = 100.0

    fun makeRequest(nome: String) {
        //REQUEST: http://nominatim.openstreetmap.org/search?q=ADDRESS_HERE&format=json
        //MAX 1 per secondo

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            val mURL = URL("https://nominatim.openstreetmap.org/search?q=$nome&format=json")

            with(mURL.openConnection() as HttpURLConnection) {
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()

                    lat = response.split(",")[9].split("\"")[3].toDouble()
                    lon = response.split(",")[10].split("\"")[3].toDouble()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun findGeoPoints(): Array<GeoPoint> {
        val partenzaET: EditText = findViewById(R.id.partenzaET)
        val arrivoET: EditText = findViewById(R.id.destinazioneET)

        val partenza = partenzaET.text.toString()
        val arrivo = arrivoET.text.toString()

        try {
            val numeri = partenza.split(",")
            //val numeri = "42.9637049, 13.8756445".split(",")
            val num1 = parseDouble(numeri[0])
            lat = num1
            lon = parseDouble(numeri[1])
        } catch (e: Exception) {
            makeRequest(partenza.replace(" ", "-").replace("\'", "%27"))
        }

        val geoPointPartenza = GeoPoint(lat, lon)

        Thread.sleep(1000)

        try {
            val numeri = arrivo.split(",")
            //val numeri = "45.8877067, 13.4895493".replace(" ", "").split(",")
            val num1 = parseDouble(numeri[0])
            lat = num1
            lon = parseDouble(numeri[1])
        } catch (e: Exception) {
            makeRequest(arrivo.replace(" ", "-").replace("\'", "%27"))
        }

        val geoPointArrivo = GeoPoint(lat, lon)

        return arrayOf(
            geoPointPartenza,
            geoPointArrivo
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mappa)

        val queryTV: TextView = findViewById(R.id.query)
        queryTV.text = getString(R.string.inserire_indirizzi_coordinate)

        val map = findViewById<MapView>(R.id.mapView)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        val startPoint = GeoPoint(44.6568, 10.9202)

        val mapController = map.controller
        mapController.setCenter(startPoint)
        mapController.setZoom(9.0)
    }

    private fun getRoad(map: MapView, startPoint: GeoPoint, endPoint: GeoPoint): Road {
        Configuration.getInstance().userAgentValue = this.packageName
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val roadManager = OSRMRoadManager(this, this.packageName)
        roadManager.setMean(OSRMRoadManager.MEAN_BY_BIKE)

        map.controller.setCenter(startPoint)
        val wayPoints = arrayListOf(startPoint, endPoint)

        return roadManager.getRoad(wayPoints)
    }

    fun onClick(view: View) {
        val map: MapView = findViewById(R.id.mapView)
        val error: TextView = findViewById(R.id.query)
        val points = findGeoPoints()
        val startPoint = points[0]
        val endPoint = points[1]
//      val startPoint = GeoPoint(42.964026, 13.876690)
//      val endPoint = GeoPoint(42.787341, 13.581745)

        if (startPoint.latitude != 100.0 && startPoint.longitude != 100.0)
            if (endPoint.latitude != 100.0 && endPoint.longitude != 100.0)
                road = getRoad(map, startPoint, endPoint)
            else
                error.text = getString(R.string.errore_destinazione)
        else
            error.text = getString(R.string.errore_partenza)

        val roadOverlay = RoadManager.buildRoadOverlay(road)
        map.overlays.add(roadOverlay)

        val button = findViewById<Button>(R.id.confermaButton)
        button.visibility = View.VISIBLE

        map.invalidate()
    }

    fun onConferma(view: View) {
        val intent = Intent(this, IstruzioniActivity::class.java)
        intent.putExtra("EXTRA_ROAD", road)
        val size = road.mNodes.size
        MyFileManager.showDisclaimer(
            this,
            "Attendere prego",
            "Il processo richiederà almeno $size secondi, non chiudere l'applicazione."
        )
        // IL PROCESSO SI VEDE AVANZARE NELLA CONSOLE
        startActivity(intent)
    }
}