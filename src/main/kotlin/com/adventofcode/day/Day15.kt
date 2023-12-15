package com.adventofcode.day

class Day15(inputFile: String): DayXX(inputFile) {
    private data class Lens(val label: String, var amount: Int)
    private data class Box(val id: Int, var lens: MutableList<Lens>)

    override fun part1(): Int = input.first().split(",")
        .sumOf { hash(it) }

    override fun part2(): Int {
        val boxes = arrayListOf<Box>()
        input.first().split(",").forEach {
            if (it.contains("=")) {
                val (label, amount) = it.split("=")
                val boxNumber = hash(label) + 1
                val box = boxes.find { it.id == boxNumber }
                if (box != null) {
                    val lens = box.lens.find { it.label == label }
                    if (lens != null) {
                        lens.amount = amount.toInt()
                    } else {
                        box.lens.add(Lens(label, amount.toInt()))
                    }
                } else {
                    boxes.add(
                        Box(
                            boxNumber,
                            arrayListOf(Lens(label, amount.toInt()))
                        )
                    )
                }
            } else {
                val label = it.dropLast(1)
                val boxNumber = hash(label) + 1
                val box = boxes.find { it.id == boxNumber }
                if (box != null) {
                    val lens = box.lens.find { it.label == label }
                    if (lens != null) {
                        box.lens = box.lens.filter { it.label != label }.toMutableList()
                    }
                }
            }
        }

        var result = 0
        boxes.forEach { (id, lens) ->
            lens.forEachIndexed { index, (_, amount) ->
                result += id * (index + 1) * amount
            }
        }

        return result
    }

    private fun hash(input: String): Int {
        var result = 0
        input.forEach {
            result += it.code
            result *= 17
            result %= 256
        }

        return result
    }
}

fun main() {
    Day15("day15").solve()
}