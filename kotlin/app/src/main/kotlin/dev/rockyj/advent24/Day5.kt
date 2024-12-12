package dev.rockyj.advent24

fun main() {
    val contents = fileToArr("day5_2.txt")

    val rules = contents.take(1176).map{ it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }
    val pages = contents.subList(1177, contents.size) .map{ it.split(",") }.map { it.map { e -> e.toInt() } }

    part1(rules, pages)
    part2(rules, pages)
}

private fun findViolations(pagePair: Pair<Int, Int>, rules: List<Pair<Int, Int>>): Pair<Int, Int>? {
    val violation = rules
        .find { rule -> pagePair.first == rule.second && pagePair.second == rule.first }

    return violation
}

private fun pageListToPagePairs(pageList: List<Int>): MutableList<Pair<Int, Int>> {
    val pagePairs = mutableListOf<Pair<Int, Int>>()

    pageList.forEachIndexed { index, page ->
        var i = index
        while (i < pageList.size - 1) {
            pagePairs.plusAssign(Pair(page, pageList[i + 1]))
            i += 1
        }
    }

    return pagePairs
}

private fun areAllPagePairsValid(rules: List<Pair<Int, Int>>, pageList: List<Int>): Boolean {
    val pp = pageListToPagePairs(pageList).map { findViolations(it, rules) }
    return pp.all { it == null }
}

private fun findAllViolations(rules: List<Pair<Int, Int>>, pageList: List<Int>): List<Pair<Int, Int>?> {
    val pp = pageListToPagePairs(pageList).map { findViolations(it, rules) }
    return pp
}

fun fixViolations(rules: List<Pair<Int, Int>>, violations: List<Pair<Int, Int>>, page: List<Int>): List<Int> {
//    println("---------")
//    println(page)
//    println(violations)

    val newPage = page.toMutableList()

    violations.forEach {
        val i1 = newPage.indexOf(it.first)
        val i2 = newPage.indexOf(it.second)

        newPage[i1] = it.second
        newPage[i2] = it.first
    }

    val newViolations = findAllViolations(rules, newPage).filterNotNull()

    return if (newViolations.isEmpty()) {
        newPage
    } else {
        fixViolations(rules, newViolations, newPage)
    }
}

private fun part1(rules: List<Pair<Int, Int>>, pages: List<List<Int>>) {
    val valid = pages.filter { page -> areAllPagePairsValid(rules, page) }
    println(valid.map { lst -> lst[lst.size / 2] }.sum())
}

private fun part2(rules: List<Pair<Int, Int>>, pages: List<List<Int>>) {
    val validPages = pages.map { page ->
        val violations = findAllViolations(rules, page).filterNotNull()

        if (violations.isEmpty()) {
            null
        } else {
            return@map fixViolations(rules, violations, page)
        }
    }.filterNotNull()

    println(validPages.map { lst -> lst[lst.size / 2] }.sum())
}


