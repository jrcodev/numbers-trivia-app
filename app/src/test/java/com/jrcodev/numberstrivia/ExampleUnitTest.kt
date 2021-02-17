package com.jrcodev.numberstrivia

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val test = "42 is the number of laws of cricket."
        println(test.createNumberTrivia())
    }
}