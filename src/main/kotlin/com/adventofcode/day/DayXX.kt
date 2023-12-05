package com.adventofcode.day

import com.adventofcode.util.println
import com.adventofcode.util.readInput

abstract class DayXX(inputFile: String) {
    protected val input = readInput(inputFile)

    protected abstract fun part1(): Any
    protected abstract fun part2(): Any

    fun solve(part: Int = 0) {
        if (part == 0 || part == 1) part1().println()
        if (part == 0 || part == 2) part2().println()
    }
}