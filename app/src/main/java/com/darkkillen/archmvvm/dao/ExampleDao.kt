package com.darkkillen.archmvvm.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.darkkillen.archmvvm.vo.Example


@Dao
abstract class ExampleDao : BaseDao<Example>() {

    @Transaction
    @Query("SELECT * FROM example")
    abstract fun getExamples(): LiveData<List<Example>>

}