package com.adventofcode.day

import kotlin.math.min

class Day05(inputFile: String) : DayXX(inputFile) {
    private data class FilterMap(
        val dest: Long, val source: Long, val range: Long)

    private data class SeedRange(val start: Long, val end: Long) {
        fun getRange() = start..end
    }

    override fun part1(): Long {
        val (seeds, filterMaps) = getSeedsAndMaps(input.filter { it.isNotEmpty() })

        var current = seeds
        filterMaps.forEach { current = goTroughMaps(current, it) }

        return current.min()
    }

    override fun part2(): Long {
        val (seeds, filterMaps) = getSeedsAndMaps(input.filter { it.isNotEmpty() })
        val seedRanges = getSeedRanges(seeds)

        var min = Long.MAX_VALUE

        seedRanges.forEach {
            for (i in it.getRange()) {
                var temp = i

                filterMaps.forEach {
                    temp = goTroughMap(temp, it)
                }

                min = min(temp, min)
            }
        }

        return min
    }

    private fun getSeedsAndMaps(input: List<String>)
            : Pair<List<Long>, List<List<FilterMap>>> {
        val filterMaps: MutableList<List<FilterMap>> = ArrayList()
        var tempMap: ArrayList<FilterMap> = ArrayList()

        val seeds = input.first()
            .split(": ")[1]
            .split(" ")
            .map { it.toLong() }

        input.drop(1).forEach { line ->
            if (line.contains("map:")) {
                if (tempMap.isNotEmpty()) filterMaps.add(tempMap)
                tempMap = ArrayList()
            } else  {
                val map = line.split(" ").map { it.toLong() }
                tempMap.add(FilterMap(map[0], map[1], map[2]))
            }
        }

        if (tempMap.isNotEmpty()) filterMaps.add(tempMap)

        return Pair(seeds, filterMaps)
    }

    private fun getSeedRanges(seeds: List<Long>): List<SeedRange> {
        val result = ArrayList<SeedRange>()

        seeds.forEachIndexed { index, l ->
            if (index % 2 == 0) {
                result.add(SeedRange(l, l + seeds[index + 1] - 1))
            }
        }

        return result
    }

    private fun goTroughMaps(
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

    private fun goTroughMap(start: Long, filterMaps: List<FilterMap>): Long {
        var new: Long? = null
        for (filterMap in filterMaps) {
            new = convert(start, filterMap)
            if (new != null) break
        }

        return new ?: start
    }

    private fun convert(start: Long, filterMap: FilterMap): Long? {
        if (start in filterMap.source..<filterMap.source + filterMap.range) {
            return start - filterMap.source + filterMap.dest
        }

        return null
    }
}