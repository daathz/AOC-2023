package com.adventofcode.day

class Day01(inputFile: String) : DayXX(inputFile) {
    override fun part1() = input
        .map { it.filter { char -> char.isDigit() } }
        .map { it.first().toString() + it.last().toString() }
        .sumOf { it.toInt() }

    override fun part2() = input
        .map { digitStringToInt(it) }
        .map { it.filter { char -> char.isDigit() } }
        .map { it.first().toString() + it.last().toString() }
        .sumOf { it.toInt() }

    private fun digitStringToInt(input: String): String = input
        .replace("one", "o1e")
        .replace("two", "t2o")
        .replace("three", "t3e")
        .replace("four", "f4r")
        .replace("five", "f5e")
        .replace("six", "s6x")
        .replace("seven", "s7n")
        .replace("eight", "e8t")
        .replace("nine", "n9e")
}