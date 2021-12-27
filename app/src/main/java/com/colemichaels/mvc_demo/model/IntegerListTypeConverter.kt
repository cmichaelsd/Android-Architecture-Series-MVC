package com.colemichaels.mvc_demo.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListTypeConverter {
    @TypeConverter
    fun stringToIntegerList(data: String?): MutableList<Int> {
        if (data == null || data.isEmpty() || data == "null")
            return mutableListOf()

        val listType = object : TypeToken<List<Int>>() {}.type

        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun integerListToString(someObjects: List<Int>?): String =
        Gson().toJson(someObjects)
}