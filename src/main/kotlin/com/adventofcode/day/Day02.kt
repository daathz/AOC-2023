package com.adventofcode.day

class Day02(inputFile: String): DayXX(inputFile) {
    private data class Game(val id: Int, val set: String)
    private data class Throw(val red: Int, val green: Int, val blue: Int)

    override fun part1(): Int = input
        .map { it.split(": ") }
        .map { Game(it[0].split(" ")[1].toInt(), it[1]) }
        .filter { isPossible(it.set) }
        .sumOf { it.id }

    override fun part2(): Int = input
        .map { it.split(": ") }
        .map { Game(it[0].split(" ")[1].toInt(), it[1]) }
        .map { powerOfMin(it.set) }
        .sumOf { it }

    private fun getMaxThrows(set: String): Throw {
        val throws = set.split("; ")

        return throws.map { parseThrow(it) }
            .reduce { acc, next ->
                Throw(
                    maxOf(acc.red, next.red),
                    maxOf(acc.green, next.green),
                    maxOf(acc.blue, next.blue)
                )
            }
    }

    private fun isPossible(set: String): Boolean {
        val (red, green, blue) = getMaxThrows(set)

        return red <= 12 && green <= 13 && blue <= 14
    }

    private fun powerOfMin(set: String): Int {
        val (red, green, blue) = getMaxThrows(set)

        return red * green * blue
    }

    private fun parseThrow(string: String): Throw {
        val set = string.split(", ")
        var red = 0; var blue = 0; var green = 0

        set.forEach {
            val split = it.split(" ")

            val number = split[0].toInt()
            val color = split[1]

            when (color) {
                "red" -> red = number
                "blue" -> blue = number
                "green" -> green = number
            }
        }

        return Throw(red, green, blue)
    }
}