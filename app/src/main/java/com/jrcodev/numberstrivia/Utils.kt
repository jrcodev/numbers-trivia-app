package com.jrcodev.numberstrivia

import com.jrcodev.numberstrivia.storage.NumberTrivia

fun String.createNumberTrivia(): NumberTrivia {
    val number = split(' ')[0].trim()
    val trivia = split(number)[1].trim()

    return NumberTrivia(number = number, desc = trivia)
}

enum class Status {
    Start,
    Loading,
    Error,
    Result,
    Unknown
}

fun mapIntToEnum(value: Int) = when (value) {
    0 -> Status.Start
    1 -> Status.Loading
    2 -> Status.Error
    3 -> Status.Result
    else -> Status.Unknown
}

sealed class SearchStatus {
    object Start : SearchStatus()
    object Loading : SearchStatus()
    object Error : SearchStatus()
    class Result(val data: String) : SearchStatus()
}
