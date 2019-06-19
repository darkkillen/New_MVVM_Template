package com.darkkillen.archmvvm.db.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

class StringListConverter: BaseListConverter<String>() {

    @TypeConverter
    override fun fromString(value: String): ArrayList<String> {
        val typeToken = object : TypeToken<ArrayList<String>>() {}
        val type = typeToken.type as ParameterizedType
        val list = GsonBuilder().create().fromJson<ArrayList<String>>(value, type)
        return list ?: ArrayList()
    }

}