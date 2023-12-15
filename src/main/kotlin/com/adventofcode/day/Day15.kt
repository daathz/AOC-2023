package com.adventofcode.day

class Day15(inputFile: String): DayXX(inputFile) {
    override fun part1(): Int = input.first().split(",")
        .sumOf { hash(it) }

    override fun part2() {
    }

    private fun hash(input: String): Int {
        var result = 0
        input.forEach {
            result += it.toInt()
            result *= 17
            result %= 256
        }

        return result
    }
}

fun main() {
    Day15("day15").solve(1)
}