package com.jrcodev.numberstrivia.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NumberTriviaDao {
    @Insert
    fun insert(trivia: NumberTrivia)

    @Query("SELECT DISTINCT * FROM number_trivia ORDER BY id DESC")
    fun getAllTrivia(): LiveData<List<NumberTrivia>>

    @Delete
    fun delete(trivia: NumberTrivia)

    @Query("DELETE from number_trivia")
    fun clear()
}