package com.jrcodev.numberstrivia.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number_trivia")
data class NumberTrivia(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo val number: String,
    @ColumnInfo val desc: String
)