package com.adventofcode.day

class Day07(inputFile: String): DayXX(inputFile) {
    private data class Hand(val hand: String, val bid: Long) {
        fun getRank(): Int {
            val distinct = hand.map { it }.distinct()

            return when (distinct.size) {
                1 -> 14
                2 -> {
                    var typeA = 0
                    var typeB = 0
                    hand.forEach {
                        if (it == distinct[0]) typeA++ else typeB++
                    }
                    return if (typeA == 4 || typeB == 4) 12 else 10
                }
                3 -> {
                    var typeA = 0
                    var typeB = 0
                    var typeC = 0
                    hand.forEach {
                            when (it) {
                            distinct[0] -> typeA++
                            distinct[1] -> typeB++
                            else -> typeC++
                        }
                    }
                    return if (typeA == 3 || typeB == 3 || typeC == 3) 8 else 6
                }
                4 -> 4
                5 -> 2
                else -> 0
            }
        }
        fun getRankJoker(): Int {
            val jCount = hand.filter { it == 'J' }.length
            // QQQQJ
            if (this.getRank() == 12 && jCount == 1) return 14
            // JJJJQ
            if (this.getRank() == 12 && jCount == 4) return 14
            // QQQJJ
            if (this.getRank() == 10 && jCount == 2) return 14
            // JJJQQ
            if (this.getRank() == 10 && jCount == 3) return 14
            // QQQJA
            if (this.getRank() == 8 && jCount == 1) return 12
            // JJJQA
            if (this.getRank() == 8 && jCount == 3) return 12
            // QQAJJ
            if (this.getRank() == 6 && jCount == 2) return 12
            // QQAAJ
            if (this.getRank() == 6 && jCount == 1) return 10
            // QQABJ
            if (this.getRank() == 4 && jCount == 1) return 8
            // JJABQ
            if (this.getRank() == 4 && jCount == 2) return 8
            // 1234J
            if (this.getRank() == 2 && jCount == 1) return 4

            return this.getRank()
        }
    }
    override fun part1(): Long = input
        .map { it.split(" ") }
        .map { Hand(it[0], it[1].toLong()) }
        .map { it to it.getRank() }
        .sortedWith(handComparator)
        .mapIndexed { index, pair ->
            (index + 1) * pair.first.bid
        }
        .sum()

    override fun part2() = input
        .map { it.split(" ") }
        .map { Hand(it[0], it[1].toLong()) }
        .map { it to it.getRankJoker() }
        .sortedWith(jokerComparator)
        .mapIndexed { index, pair ->
            (index + 1) * pair.first.bid
        }
        .sum()

    private val handComparator = object: Comparator<Pair<Hand, Int>> {
        override fun compare(h1: Pair<Hand, Int>, h2: Pair<Hand, Int>): Int {
            if (h1.second > h2.second)  return 1
            else if (h1.second < h2.second) return -1
            else {
                val h1c = replaceCharsWithComparable(h1.first.hand)
                val h2c = replaceCharsWithComparable(h2.first.hand)

                for (index in 0..5) {
                    val firstIndex = h1c[index]
                    val secondIndex = h2c[index]
                    when {
                        firstIndex > secondIndex -> return 1
                        firstIndex < secondIndex -> return -1
                    }
                }
            }

            return 0
        }
    }

    private val jokerComparator = object: Comparator<Pair<Hand, Int>> {
        override fun compare(h1: Pair<Hand, Int>, h2: Pair<Hand, Int>): Int {
            if (h1.second > h2.second)  return 1
            else if (h1.second < h2.second) return -1
            else {
                val h1c = replaceCharsWithComparable(h1.first.hand, true)
                val h2c = replaceCharsWithComparable(h2.first.hand, true)

                for (index in 0..5) {
                    val firstIndex = h1c[index]
                    val secondIndex = h2c[index]
                    when {
                        firstIndex > secondIndex -> return 1
                        firstIndex < secondIndex -> return -1
                    }
                }
            }

            return 0
        }
    }

    private fun replaceCharsWithComparable(
        input: String, joker: Boolean = false): String {
        val result = input
            .replace("T", "B")
            .replace("Q", "D")
            .replace("K", "E")
            .replace("A", "F")

        return if (joker)
            result.replace("J", "1") else
            result.replace("J", "C")
    }
}