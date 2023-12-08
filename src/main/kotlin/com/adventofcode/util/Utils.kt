package com.adventofcode.util

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

const val FOREGROUND_CHAR = '█'
const val BACKGROUND_CHAR = '░'

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Char.toNumber() = toString().toInt()

fun String.isNumeric(): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return this.matches(regex)
}

fun <T> MutableList<T>.pop(): T {
    val result = this.first()
    this.removeFirst()

    return result
}

fun gcd(a: Long, b: Long): Long {
    var num1 = a
    var num2 = b

    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }

    return num1
}

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

fun List<Long>.lcm(): Long = reduce { acc, l -> lcm(acc, l) }