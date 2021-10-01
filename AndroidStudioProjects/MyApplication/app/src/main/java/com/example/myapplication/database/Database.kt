package com.example.myapplication.database
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.src.Bike

class Database(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int):
    SQLiteOpenHelper(context, "bella.db", null, 1){

    val BIKE_TABLE = "BIKE_TABLE"
    val COLUMN_BIKE_NAME = "BIKE_NAME"
    val COLUMN_BIKE_MASS = "BIKE_MASS"
    val COLUMN_BIKE_AREA = "BIKE_AREA"
    val COLUMN_BIKE_TYRE0 = "BIKE_TYRE1"
    val COLUMN_BIKE_TYRE1 = "BIKE_TYRE2"
    val COLUMN_BIKE_CRANK = "BIKE_CRANK"
    val COLUMN_BIKE_GEARS = "BIKE_GEARS"
    val COLUMN_BIKE_ENGINE = "BIKE_ENGINE"
    val COLUMN_BIKE_BATTERY = "BIKE_BATTERY"

    val db = context?.openOrCreateDatabase("bici.db", Context.MODE_PRIVATE, null)

    override fun onCreate(db: SQLiteDatabase?) {
        var createTableStatement = "CREATE TABLE " + BIKE_TABLE + "(" +
                COLUMN_BIKE_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_BIKE_MASS + " INTEGER, " +
                COLUMN_BIKE_AREA + " REAL, " +
                COLUMN_BIKE_TYRE0 + " REAL, " +
                COLUMN_BIKE_TYRE1 + " REAL, " +
                COLUMN_BIKE_CRANK + " REAL, " +
                COLUMN_BIKE_GEARS + " NAME, " +
                COLUMN_BIKE_ENGINE + " NAME, " +
                COLUMN_BIKE_BATTERY + " NAME)"
        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addBike(bike: Bike): Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_BIKE_NAME, bike.name)
        cv.put(COLUMN_BIKE_MASS, bike.mass)
        cv.put(COLUMN_BIKE_TYRE0, bike.tyre0)
        cv.put(COLUMN_BIKE_TYRE1, bike.tyre1)
        cv.put(COLUMN_BIKE_CRANK, bike.crank)
        cv.put(COLUMN_BIKE_GEARS, bike.gears.name)
        cv.put(COLUMN_BIKE_ENGINE, bike.engine.name)
        cv.put(COLUMN_BIKE_BATTERY, bike.battery.name)

        return db.insert(BIKE_TABLE, null, cv) == (-1).toLong()

    }
}