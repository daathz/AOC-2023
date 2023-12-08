package com.adventofcode.day

import com.adventofcode.util.lcm

class Day08(inputFile: String): DayXX(inputFile) {

    private data class Node(
        val start: String,
        val left: String,
        val right: String
    )

    override fun part1(): Long {
        val instructions = input.first()
        val network = getNetwork(input.drop(2))

        return stepsToDestination(
            network.first { it.start == "AAA" },
            network,
            instructions,
            stopCondition = { it == "ZZZ" })
    }

    override fun part2(): Long {
        val instructions = input.first()
        val network = getNetwork(input.drop(2))

        val startNodes = network.filter { it.start.last() == 'A' }

        return startNodes.map { node ->
            stepsToDestination(node, network, instructions,
                stopCondition = { it.last() == 'Z' })
        }.lcm()
    }

    private fun getNetwork(input: List<String>): List<Node> = input.map {
        val (node, destinations) = it.split((" = "))

        val (left, right) = destinations
            .replace("(", "")
            .replace(")", "")
            .split(", ")

        Node(node, left, right)
    }

    private fun stepsToDestination(
        start: Node, network: List<Node>, instructions: String,
        stopCondition: (String) -> Boolean): Long {
        var steps = 0L
        var current = start

        while (true) {
            val instruction =
                instructions[(steps % instructions.length).toInt()]

            val currentString = when (instruction) {
                'L' -> current.left
                else -> current.right
            }

            steps++
            if (stopCondition(currentString)) break

            current = network.first { it.start == currentString }
        }

        return steps
    }
}