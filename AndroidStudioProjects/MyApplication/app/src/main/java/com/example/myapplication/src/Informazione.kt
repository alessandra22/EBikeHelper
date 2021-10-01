package com.example.myapplication.src

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


class Informazione(
    val powEngine: Double,
    val speed: Double,
    val alevName: String,
    val sh: Int,
    val tq: Double,
    val powBiker: Double,
    val cd: Double
): Serializable