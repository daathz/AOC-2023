package com.adventofcode.day

private typealias Pattern = List<String>
class Day13(inputFile: String): DayXX(inputFile) {
    override fun part1(): Int = getPatterns(input).sumOf { getReflection(it) }

    override fun part2() {
    }

    private fun getPatterns(input: List<String>): ArrayList<Pattern> {
        val result = arrayListOf<Pattern>()
        var temp = arrayListOf<String>()
        input.forEach {
            if (it.isNotEmpty()) {
                temp.add(it)
            } else {
                result.add(temp)
                temp = arrayListOf()
            }
        }
        result.add(temp)

        return result
    }

    private fun getReflection(pattern: Pattern): Int {
        for (i in 0..<pattern.size - 1) {
            var top = i
            var bottom = i + 1

            try {
                while (pattern[top] == pattern[bottom]) {
                    top--
                    bottom++
                }
            } catch (e: IndexOutOfBoundsException) {
                return (i + 1) * 100
            }
        }

        val transposedPattern = getTransposedPattern(pattern)
        for (i in 0..<transposedPattern.size - 1) {
            var top = i
            var bottom = i + 1

            try {
                while (transposedPattern[top] == transposedPattern[bottom]) {
                    top--
                    bottom++
                }
            } catch (e: IndexOutOfBoundsException) {
                return i + 1
            }
        }

        return 0
    }

    private fun getTransposedPattern(pattern: Pattern): Pattern {
        val result = arrayListOf<String>()
        var temp = arrayListOf<Char>()

        for (i in 0..<pattern[0].length) {
            for (j in pattern.indices) {
                temp.add(pattern[j][i])
            }
            result.add(temp.joinToString(""))
            temp = arrayListOf()
        }

        return result
    }
}

fun main() {
    Day13("day13").solve(1)
}