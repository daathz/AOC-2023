package com.adventofcode.day

class Day03(inputFile: String) : DayXX(inputFile) {
    private data class PartRange(val from: Int, val to: Int)
    private data class PartSymbol(val row: Int, val column: Int)

    private data class PartNumber(
        val value: Int,
        val row: Int,
        val column: PartRange
    )

    override fun part1(): Int {
        val numbers = getNumbers(input)
        val symbols = getSymbols(input)

        return getAdjacentPartNumbers(numbers, symbols)
            .sum()
    }

    override fun part2(): Int {
        val numbers = getNumbers(input)
        val symbols = getSymbols(input, '*')

        return getAdjacentPartNumbers(numbers, symbols,
            partLimit = 2,
            reducer = { acc, next -> acc * next }
        ).sum()
    }

    private fun getNumbers(input: List<String>): List<PartNumber> {
        val numbers = ArrayList<PartNumber>()

        input.forEachIndexed { stringIndex, string ->
            var num = ""

            string.forEachIndexed { charIndex, char ->
                if (char.isDigit()) {
                    num += char
                } else if (num.isNotEmpty()) {
                    val actualNum = num.toInt()

                    numbers.add(
                        PartNumber(
                            actualNum,
                            stringIndex,
                            PartRange(
                                charIndex - num.length,
                                charIndex - 1
                            )
                        )
                    )

                    num = ""
                }
            }

            if (num.isNotEmpty()) {
                val actualNum = num.toInt()
                numbers.add(
                    PartNumber(
                        actualNum,
                        stringIndex,
                        PartRange(
                            string.length - num.length,
                            string.length - 1
                        )
                    )
                )

                num = ""
            }
        }

        return numbers
    }

    private fun getAdjacentPartNumbers(
        numbers: List<PartNumber>,
        symbols: List<PartSymbol>,
        partLimit: Int? = null,
        reducer: ((acc: Int, next: Int) -> Int)? = null
    ): HashSet<Int> {
        val adjacentPartNumbers = HashSet<Int>()

        symbols.forEach { symbol ->
            val currentAdjacentParts = HashSet<Int>()

            numbers.forEach { partNumber ->
                when (partNumber.row) {
                    symbol.row - 1, symbol.row + 1 -> {
                        when {
                            symbol.column >= partNumber.column.from && symbol.column <= partNumber.column.to -> {
                                currentAdjacentParts.add(partNumber.value)
                            }
                            partNumber.column.to + 1 == symbol.column -> {
                                currentAdjacentParts.add(partNumber.value)
                            }
                            partNumber.column.from - 1 == symbol.column -> {
                                currentAdjacentParts.add(partNumber.value)
                            }
                        }
                    }
                    symbol.row -> {
                        when (symbol.column) {
                            partNumber.column.to + 1, partNumber.column.from - 1 -> {
                                currentAdjacentParts.add(partNumber.value)
                            }
                        }
                    }
                }
            }

            when {
                partLimit == null -> {
                    adjacentPartNumbers.addAll(currentAdjacentParts)
                }
                currentAdjacentParts.size == partLimit && reducer != null -> {
                    adjacentPartNumbers.add(currentAdjacentParts.reduce(reducer))
                }
            }
        }

        return adjacentPartNumbers
    }

    private fun getSymbols(
        input: List<String>,
        specificSymbol: Char? = null
    ): List<PartSymbol> {
        val symbols = ArrayList<PartSymbol>()

        input.forEachIndexed { stringIndex, string ->
            string.forEachIndexed { charIndex, char ->
                if (specificSymbol != null && specificSymbol == char) {
                    symbols.add(PartSymbol(stringIndex, charIndex))
                } else if (char != '.' && !char.isDigit()) {
                    symbols.add(PartSymbol(stringIndex, charIndex))
                }
            }
        }

        return symbols
    }
}