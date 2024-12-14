package dev.rockyj.advent24

fun main() {
    val contents = fileToArr("day8_2.txt")

    val matrix = contents
        .map { it.trim().split("").filter { x -> x != "" } }
        .map { list -> list.filter { it !="" } }
        .mapIndexed { idx1, list -> list.mapIndexed { idx2, item -> mapOf(Pair(idx2, idx1) to item) } }
        .flatten()
        .reduce { acc, map -> acc.plus(map) }

    val width = contents.first().trim().split("").filter { x -> x != "" }.size
    val height = contents.size

    println(part1(matrix, width, height))
    // println(part2(matrix, width, height))
}

private fun findAntinodes(matrix: Map<Pair<Int, Int>, String>, width: Int, height: Int, freq: String): List<Pair<Int, Int>> {
    val antennas = matrix.keys.filter { matrix[it] == freq }
    val antiNodes = mutableListOf<Pair<Int, Int>>()

    antennas.forEach { node1 ->
        antennas.forEach { node2 ->
            if (node1 != node2) {
                val x = 2 * node1.first - node2.first
                val y = 2 * node1.second - node2.second

                if(x >= 0 && x < width && y >= 0 && y < height) {
                    antiNodes.plusAssign(Pair(x, y))
                }
            }
        }
    }

    return antiNodes.toList()
}

private fun part1(matrix: Map<Pair<Int, Int>, String>, width: Int, height: Int): Int {
    val frequencies = matrix.keys.filter { matrix[it] != "." }.map { matrix[it] }.distinct()

    val res = frequencies.map { findAntinodes(matrix, width, height, it!!) }.flatten()

    // println(res.distinct())

    return res.distinct().size
}