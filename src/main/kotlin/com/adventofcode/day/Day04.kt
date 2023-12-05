package com.adventofcode.day

import kotlin.math.pow

class Day04(inputFile: String): DayXX(inputFile) {
    private data class Card(
        val id: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {
        fun getWinningNumbers(): Int = winningNumbers.intersect(numbers).size
    }

    override fun part1(): Int = input
        .map(::parseCard)
        .sumOf { it.getScore() }

    override fun part2(): Int {
        val cards = input.map(::parseCard)

        cards.forEach { solveCard(it, cards) }

        return scratches
    }

    private fun Card.getScore(): Int =
        2.0.pow(this.getWinningNumbers() - 1).toInt()

    private fun parseCard(input: String): Card {
        val split = input.split(": ")
        val id = split[0].split("\\s+".toRegex())[1].toInt()

        val allNumbers = split[1].split("|")
        val winningNumbers = allNumbers[0]
            .trim()
            .split("\\s+".toRegex())
            .map { it.toInt() }
            .toSet()

        val numbers = allNumbers[1]
            .trim()
            .split("\\s+".toRegex())
            .map { it.toInt() }
            .toSet()

        return Card(id, winningNumbers, numbers)
    }

    private fun getScratchedCardNumbers(card: Card): List<Int> {

        val ids = ArrayList<Int>()

        for (i in 0..< card.getWinningNumbers()) {
            ids.add(card.id + i + 1)
        }

        scratches++

        return ids
    }

    private fun getWonCards(root: List<Card>, ids: List<Int>): List<Card> {
        val res = ArrayList<Card>()

        for (i in ids) {
            res.add(root.first { it.id == i })
        }

        return res
    }

    private fun solveCard(card: Card, root: List<Card>) {
        val nums = getScratchedCardNumbers(card)
        val cards = getWonCards(root, nums)

        cards.forEach { solveCard(it, root) }
    }

    companion object {
        private var scratches = 0
    }
}