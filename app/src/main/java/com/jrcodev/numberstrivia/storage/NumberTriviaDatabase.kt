package com.jrcodev.numberstrivia.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NumberTrivia::class], version = 1, exportSchema = false)
abstract class NumberTriviaDatabase : RoomDatabase() {
    abstract val dao: NumberTriviaDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: NumberTriviaDatabase


        fun getInstance(context: Context): NumberTriviaDatabase = synchronized(this) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NumberTriviaDatabase::class.java,
                    "number_trivia_db"
                ).fallbackToDestructiveMigration().build()
            }

            return INSTANCE
        }
    }
}