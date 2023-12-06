package com.adventofcode.day

import com.adventofcode.util.println

class Day06(inputFile: String): DayXX(inputFile) {
    private data class Race(val time: Long, val distance: Long)
    private val whiteSpace = "\\s+".toRegex()
    override fun part1(): Long = parseRaces(input)
        .map { beatRecordHowManyTimes(it) }
        .reduce { acc, next -> acc * next }

    override fun part2(): Long =
        beatRecordHowManyTimes(parseAsOneRace(input)!!)

    private fun parseRaces(input: List<String>): List<Race> {
        val races = ArrayList<Race>()

        val map = input.map { it.split(":")[1].trim() }
            .map { it.split(whiteSpace) }
            .map { it.map { it.toLong() } }

        for (i in 0..<map[0].size) {
            races.add(Race(map[0][i], map[1][i]))
        }

        return races
    }

    private fun parseAsOneRace(input: List<String>): Race? {
        val map = input.map { it.split(":")[1].trim() }
            .map { it.replace(" ", "") }
            .map { it.toLong() }

        for (i in map.indices) {
            return Race(map[i], map[i + 1])
        }

        return null
    }

    private fun beatRecordHowManyTimes(race: Race): Long {
        var result = 0L

        for (i in 0..race.time) {
            val remainingTime = race.time - i

            val traveledDistance = remainingTime * i

            if (traveledDistance > race.distance) result++
        }

        return result
    }
}