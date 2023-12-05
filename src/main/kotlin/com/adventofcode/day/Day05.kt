package com.adventofcode.day

import com.adventofcode.util.pop
import kotlin.math.max
import kotlin.math.min

class Day05(inputFile: String) : DayXX(inputFile) {
    private data class FilterMap(
        val dest: Long, val source: Long, val range: Long) {
        fun passTroughMap(value: Long): Long = value - source + dest

        fun getRange(): LongRange = source..<source + dest
    }

    private data class FilterRange(val start: Long, val end: Long)

    override fun part1(): Long {
        val (seeds, filterMapBlocks) =
            getSeedsAndMaps(input.filter { it.isNotEmpty() })

        var current = seeds
        filterMapBlocks.forEach { current = applyListTroughMaps(current, it) }

        return current.min()
    }

    override fun part2(): Long {
        val (seeds, filterMapBlocks) =
            getSeedsAndMaps(input.filter { it.isNotEmpty() })

        val seedRanges = getSeedRanges(seeds)

        return getFinalRanges(seedRanges, filterMapBlocks)
            .minOf { it.start }
    }

    private fun getSeedsAndMaps(input: List<String>)
            : Pair<List<Long>, List<List<FilterMap>>> {
        val filterMapBlocks: MutableList<List<FilterMap>> = ArrayList()
        var tempMap: ArrayList<FilterMap> = ArrayList()

        val seeds = input.first()
            .split(": ")[1]
            .split(" ")
            .map { it.toLong() }

        input.drop(1).forEach { line ->
            when {
                line.contains("map:") -> {
                    if (tempMap.isNotEmpty()) filterMapBlocks.add(tempMap)
                    tempMap = ArrayList()
                }
                else -> {
                    val map = line.split(" ").map { it.toLong() }
                    tempMap.add(FilterMap(map[0], map[1], map[2]))
                }
            }
        }

        if (tempMap.isNotEmpty()) filterMapBlocks.add(tempMap)

        return seeds to filterMapBlocks
    }

    private fun getSeedRanges(seeds: List<Long>): List<FilterRange> {
        val result = ArrayList<FilterRange>()

        seeds.chunked(2).forEach {
            result.add(FilterRange(it[0], it[0] + it[1]))
        }

        return result
    }

    private fun applyListTroughMaps(
        start: List<Long>, filterMaps: List<FilterMap>
    ): List<Long> =
        start.map {
            var new: Long? = null

            for (filterMap in filterMaps) {
                new = convert(it, filterMap)
                if (new != null) break
            }

            new ?: it
        }

    private fun convert(start: Long, filterMap: FilterMap): Long? {
        if (start in filterMap.getRange()) {
            return filterMap.passTroughMap(start)
        }

        return null
    }

    private fun getFinalRanges(
        seedRanges: List<FilterRange>, filterMapBlocks: List<List<FilterMap>>)
            : List<FilterRange> {
        var ranges = seedRanges.toMutableList()

        filterMapBlocks.forEach { filterMaps ->
            val newRange = arrayListOf<FilterRange>()
            while (ranges.size > 0) {
                val range = ranges.pop()

                var overlapped = false

                for (filterMap in filterMaps) {
                    val overlapStart = max(range.start, filterMap.source)
                    val overlapEnd =
                        min(range.end, filterMap.source + filterMap.range)

                    if (overlapStart < overlapEnd) {
                        newRange.add(
                            FilterRange(
                                filterMap.passTroughMap(overlapStart),
                                filterMap.passTroughMap(overlapEnd)
                            )
                        )

                        if (overlapStart > range.start) {
                            ranges.add(FilterRange(range.start, overlapStart))
                        }
                        if (range.end > overlapEnd) {
                            ranges.add(FilterRange(overlapEnd, range.end))
                        }

                        overlapped = true
                        break
                    }
                }

                if (!overlapped) {
                    newRange.add(range)
                }
            }
            ranges = newRange
        }

        return ranges
    }
}