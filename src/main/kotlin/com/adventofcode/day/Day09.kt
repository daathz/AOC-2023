package com.adventofcode.day

class Day09(inputFile: String): DayXX(inputFile) {
    override fun part1(): Long =
        input.map { it.split(" ").map { it.toLong() } }
            .sumOf { it.last() + getNextHistory(it) }

    override fun part2(): Long =
        input.map { it.split(" ").map { it.toLong() } }
            .sumOf { it.first() - getPreviousHistory(it) }

    private fun getNextHistory(history: List<Long>): Long {
        var lastValue = 0L
        var temp = mutableListOf<Long>()
        var current = history

        while (true) {
            for (i in 1..<current.size) {
                val diff = current[i] - current[i - 1]
                temp.add(diff)
            }

            lastValue += temp.last()

            if (temp.all { it == 0L }) break
            current = temp
            temp = mutableListOf()
        }

        return lastValue
    }

    private fun getPreviousHistory(history: List<Long>): Long {
        val firstValues = mutableListOf<Long>()
        var temp = mutableListOf<Long>()
        var current = history

        while (true) {
            for (i in 1..<current.size) {
                val diff = current[i] - current[i - 1]
                temp.add(diff)
            }

            firstValues.add(temp.first())

            if (temp.all { it == 0L }) break
            current = temp
            temp = mutableListOf()
        }

        return firstValues.reversed().reduce { acc, l ->  l - acc }
    }
}