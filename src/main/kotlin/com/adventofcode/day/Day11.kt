package com.adventofcode.day

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11(inputFile: String): DayXX(inputFile) {
    private data class Galaxy(val id: Int, val x: Long, val y: Long)
    private data class Distance(
        val g1: Galaxy,
        val g2: Galaxy,
        val distance: Long
    )

    override fun part1(): Long = getDistances(input).sumOf { it.distance } / 2

    override fun part2(): Long = getDistances(input, 1_000_000)
        .sumOf { it.distance } / 2

    private fun getGalaxies(map: List<List<Char>>): List<Galaxy> {
        val galaxies = arrayListOf<Galaxy>()

        map.forEachIndexed { row, chars ->
            chars.forEachIndexed { column, c ->
                if (c == '#') {
                    galaxies.add(Galaxy(++seq, row.toLong(), column.toLong()))
                }
            }
        }

        return galaxies
    }

    private fun getDistances(input: List<String>, replaceValue: Int = 2)
            : List<Distance> {
        val galaxies = getGalaxies(input.map { it.toList() })
        val (emptyRows, emptyColumns) = getEmptyRowsAndColumns(input)
        val result = arrayListOf<Distance>()

        galaxies.forEach { g1 ->
            galaxies.forEach { g2 ->
                if (g1.id != g2.id) {
                    var extraY = 0L
                    var extraX = 0L

                    val maxX = max(g1.x, g2.x)
                    val minX = min(g1.x, g2.x)
                    val maxY = max(g1.y, g2.y)
                    val minY = min(g1.y, g2.y)

                    for (i in emptyRows) {

                        if (i in minX..maxX) {
                            extraX += replaceValue - 1
                        }
                    }
                    for (i in emptyColumns) {

                        if (i in minY..maxY) {
                            extraY += replaceValue - 1
                        }
                    }

                    val diff = abs(maxY - minY + extraY) +
                            abs(maxX - minX + extraX)

                    val distance = Distance(g1, g2, diff)
                    // "${g1.id} ${g2.id} $diff".println()
                    result.add(distance)
                }
            }
        }

        return result
    }

    private fun getEmptyRowsAndColumns(input: List<String>)
    : Pair<List<Int>, List<Int>> {
        val emptyRowIndices = arrayListOf<Int>()
        val emptyColumnIndices = arrayListOf<Int>()
        val temp = arrayListOf<Int>()

        input.forEachIndexed { index, s ->
            if (s.all { it == '.' }) emptyRowIndices.add(index)
        }
        input.forEach {
            it.forEachIndexed { index, c ->
                if (c == '.') temp.add(index)
            }
        }
        for (i in 0..<input[0].length) {
            if (temp.count { it == i } == input.size) {
                emptyColumnIndices.add(i)
            }

        }

        return emptyRowIndices to emptyColumnIndices
    }

    companion object {
        var seq = 0
    }
}

fun main() {
    Day11("day11").solve()
}