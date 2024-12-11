package dev.rockyj.advent24

fun main() {
    val contents = fileToArr("day4_2.txt")

    val matrix = contents
        .map { it.trim().split("") }
        .map { list -> list.filter { it !="" } }
        .mapIndexed { idx1, list -> list.mapIndexed { idx2, item -> mapOf(Pair(idx1, idx2) to item) } }

    part1(matrix)
    part2(matrix)
}

private fun part1(matrix: List<List<Map<Pair<Int, Int>, String>>>) {
    val matches = mutableListOf<String>()
    val allElems = matrix.flatten().reduce { acc, map -> acc.plus(map) }

    matrix.forEach { list -> list.forEach { map ->
        map.keys.forEach { key ->
            val horzLR = listOf(key.second, key.second + 1, key.second + 2, key.second + 3)
                .map { k2 -> allElems.get(Pair(key.first, k2))  }.filterNotNull().joinToString("")
            val horzRL = listOf(key.second, key.second - 1, key.second - 2, key.second - 3)
                .map { k2 -> allElems.get(Pair(key.first, k2))  }.filterNotNull().joinToString("")
            val vertD = listOf(key.first, key.first + 1, key.first + 2, key.first + 3)
                .map { k2 -> allElems.get(Pair(k2, key.second))  }.filterNotNull().joinToString("")
            val vertU = listOf(key.first, key.first - 1, key.first - 2, key.first - 3)
                .map { k2 -> allElems.get(Pair(k2, key.second))  }.filterNotNull().joinToString("")

            val diag1 = listOf(key, Pair(key.first-1, key.second-1), Pair(key.first-2, key.second-2), Pair(key.first-3, key.second-3))
                .map { k -> allElems.get(k)  }.filterNotNull().joinToString("")
            val diag2 = listOf(key, Pair(key.first+1, key.second+1), Pair(key.first+2, key.second+2), Pair(key.first+3, key.second+3))
                .map { k -> allElems.get(k)  }.filterNotNull().joinToString("")
            val diag3 = listOf(key, Pair(key.first+1, key.second-1), Pair(key.first+2, key.second-2), Pair(key.first+3, key.second-3))
                .map { k -> allElems.get(k)  }.filterNotNull().joinToString("")
            val diag4 = listOf(key, Pair(key.first-1, key.second+1), Pair(key.first-2, key.second+2), Pair(key.first-3, key.second+3))
                .map { k -> allElems.get(k)  }.filterNotNull().joinToString("")

            listOf(horzLR, horzRL, vertU, vertD, diag1, diag2, diag3, diag4).filter { it == "XMAS" }.forEach { matches.plusAssign(it) }
        }
    } }

    println(matches.size)
}

private fun part2(matrix: List<List<Map<Pair<Int, Int>, String>>>) {
    val matches2 = mutableListOf<String>()
    val allElems = matrix.flatten().reduce { acc, map -> acc.plus(map) }

    matrix.forEach { list ->
        list.forEach { map ->
            map.keys.forEach { key ->
                val diag1 = listOf(Pair(key.first-1, key.second-1), key, Pair(key.first+1, key.second+1))
                    .map { k -> allElems.get(k)  }.filterNotNull().joinToString("")
                val diag2 = listOf(Pair(key.first-1, key.second+1), key, Pair(key.first+1, key.second-1))
                    .map { k -> allElems.get(k)  }.filterNotNull().joinToString("")

                if (listOf("MAS", "SAM").contains(diag1) && listOf("MAS", "SAM").contains(diag2)) {
                    matches2.plusAssign("XMAS")
                }
            }
    } }

    println(matches2.size)
}