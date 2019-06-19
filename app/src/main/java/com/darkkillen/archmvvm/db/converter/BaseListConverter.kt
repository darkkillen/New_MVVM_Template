package com.darkkillen.archmvvm.db.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder

abstract class BaseListConverter<T> {

    @TypeConverter
    abstract fun fromString(value: String): ArrayList<T>

    @TypeConverter
    fun fromArrayList(list: ArrayList<T>): String {
        val json = GsonBuilder().create().toJson(list)
        return json ?: ""
    }

}