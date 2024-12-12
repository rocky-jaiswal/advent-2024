package dev.rockyj.advent24

fun main() {
    val contents = fileToArr("day6_2.txt")

    val matrix = contents
        .map { it.trim().split("").filter { x -> x != "" } }
        .map { list -> list.filter { it !="" } }
        .mapIndexed { idx1, list -> list.mapIndexed { idx2, item -> mapOf(Pair(idx2, idx1) to item) } }
        .flatten()
        .reduce { acc, map -> acc.plus(map) }

    val width = contents.first().trim().split("").filter { x -> x != "" }.size
    val height = contents.size

    println(part1(matrix, width, height))
    println(part2(matrix, width, height))
}

private fun findPath(matrix: Map<Pair<Int, Int>, String>, width: Int, height: Int): Set<Pair<Int, Int>> {
    var pos = matrix.keys.first { matrix[it] == "^" }
    var direction = "N"
    val visitedPos = mutableSetOf(pos)
    val visitedPosWithDirection = mutableSetOf(Pair("${pos.first}X${pos.second}", direction))

    while (pos.first >=0 && pos.second >= 0 && pos.first < width && pos.second < height) {
        if (matrix[pos] == "#") {
            when(direction) {
                "N" -> {
                    direction = "E"
                    pos = Pair(pos.first + 1, pos.second + 1)
                }
                "S" -> {
                    direction = "W"
                    pos = Pair(pos.first - 1, pos.second - 1)
                }
                "E" -> {
                    direction = "S"
                    pos = Pair(pos.first - 1, pos.second + 1)
                }
                "W" -> {
                    direction = "N"
                    pos = Pair(pos.first + 1, pos.second - 1)
                }
            }
        } else {
            if (direction == "N") {
                pos = Pair(pos.first, pos.second - 1)
            }
            if (direction == "S") {
                pos = Pair(pos.first, pos.second + 1)
            }
            if (direction == "E") {
                pos = Pair(pos.first + 1, pos.second)
            }
            if (direction == "W") {
                pos = Pair(pos.first - 1, pos.second)
            }
        }

        if (matrix[pos] != "#") {
            if (visitedPosWithDirection.contains(Pair("${pos.first}X${pos.second}", direction))) {
                // println(visitedPosWithDirection)
                throw RuntimeException("is a loop!")
            }

            visitedPos.plusAssign(pos)
            visitedPosWithDirection.plusAssign(Pair("${pos.first}X${pos.second}", direction))
        }
    }

    // println(visitedPos)
    return visitedPos.toSet()
}

private fun part1(matrix: Map<Pair<Int, Int>, String>, width: Int, height: Int): Int {
    val path = findPath(matrix, width, height)

    return path.size - 1
}

private fun part2(matrix: Map<Pair<Int, Int>, String>, width: Int, height: Int): Int {
    val path = findPath(matrix, width, height)
    var loops = 0

    path.forEachIndexed { index, pair ->
        if (index != 0 && index < path.size - 1) {
            try {
                val nm = matrix.toMutableMap()
                nm[pair] = "#"
                val newMat = nm.toMap()

                findPath(newMat, width, height)
            } catch (exception: RuntimeException) {
                loops += 1
            }
        }
    }

    return loops
}