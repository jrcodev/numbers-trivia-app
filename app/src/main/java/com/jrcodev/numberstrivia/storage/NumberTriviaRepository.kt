package com.jrcodev.numberstrivia.storage

import androidx.lifecycle.LiveData

class NumberTriviaRepository(private val dao: NumberTriviaDao) {
    fun insertTrivia(trivia: NumberTrivia) = dao.insert(trivia)
    fun getAllTrivia():LiveData<List<NumberTrivia>> = dao.getAllTrivia()
    fun deleteTrivia(trivia: NumberTrivia) = dao.delete(trivia)
}