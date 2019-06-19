package com.darkkillen.archmvvm.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "example")
data class Example(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: Int = 0,
        @ColumnInfo(name = "data")
        @SerializedName("data")
        var data: String = "")
        {
    @Ignore
    constructor() : this(0, "")
}