package com.adventofcode.day

class Day07(inputFile: String): DayXX(inputFile) {
    private data class Hand(val hand: String, val bid: Long) {
        fun getRank(): Int {
            val distinct = hand.map { it }.distinct()

            return when (distinct.size) {
                1 -> 7
                2 -> {
                    var typeA = 0
                    var typeB = 0
                    hand.forEach {
                        if (it == distinct[0]) typeA++ else typeB++
                    }
                    return if (typeA == 4 || typeB == 4) 6 else 5
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
                    return if (typeA == 3 || typeB == 3 || typeC == 3) 4 else 3
                }
                4 -> 2
                5 -> 1
                else -> 0
            }
        }
        fun getRankJoker(): Int {
            val jCount = hand.filter { it == 'J' }.length

            when (getRank()) {
                6 -> {
                    when (jCount) {
                        1, 4 -> return 7
                    }
                }

                5 -> {
                    when (jCount) {
                        2, 3 -> return 7
                    }
                }

                4 -> {
                    when (jCount) {
                        1, 3 -> return 6
                    }
                }

                3 -> {
                    when (jCount) {
                        2 -> return 6
                        1 -> return 5
                    }
                }

                2 -> {
                    when (jCount) {
                        1, 2 -> return 4
                    }
                }

                1 -> {
                    if (jCount == 1) return 2
                }
            }

            return getRank()
        }
    }
    override fun part1(): Long = input
        .map { it.split(" ") }
        .map { Hand(it[0], it[1].toLong()) }
        .sortedWith(handComparator)
        .mapIndexed { index, hand ->
            (index + 1) * hand.bid
        }
        .sum()

    override fun part2() = input
        .map { it.split(" ") }
        .map { Hand(it[0], it[1].toLong()) }
        .sortedWith(jokerComparator)
        .mapIndexed { index, hand ->
            (index + 1) * hand.bid
        }
        .sum()

    private val handComparator = object: Comparator<Hand> {
        override fun compare(h1: Hand, h2: Hand): Int {
            if (h1.getRank() > h2.getRank())  return 1
            else if (h1.getRank() < h2.getRank()) return -1
            else {
                val h1c = replaceCharsWithComparableOnes(h1.hand)
                val h2c = replaceCharsWithComparableOnes(h2.hand)

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

    private val jokerComparator = object: Comparator<Hand> {
        override fun compare(h1: Hand, h2: Hand): Int {
            if (h1.getRankJoker() > h2.getRankJoker())  return 1
            else if (h1.getRankJoker() < h2.getRankJoker()) return -1
            else {
                val h1c = replaceCharsWithComparableOnes(h1.hand, true)
                val h2c = replaceCharsWithComparableOnes(h2.hand, true)

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

    private fun replaceCharsWithComparableOnes(
        hand: String, joker: Boolean = false): String {
        val result = hand
            .replace("T", "B")
            .replace("Q", "D")
            .replace("K", "E")
            .replace("A", "F")

        return if (joker)
            result.replace("J", "1") else
            result.replace("J", "C")
    }
}