package dev.rockyj.advent24

import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.operator.Operator

fun main() {
    val contents = fileToArr("day7_2.txt")
        .map { it.trim().split(":").filter { x -> x != "" && x != " " } }

    val res = contents.map { it.first().toLong() }
    val op = contents.map { it.get(1).trim().split(Regex("\\s")).map { l -> l.toInt() } }

    println(part1(res, op).sum())
}

fun <T> cartesianProduct(vararg lists: List<T>): List<List<T>> {
    var result: List<List<T>> = lists[0].map { listOf(it) }

    for (i in 1 until lists.size) {
        result = result.flatMap { combination ->
            lists[i].map { element ->
                combination + element
            }
        }
    }

    return result
}

var specialMul: Operator = object : Operator("!", 2, true, Operator.PRECEDENCE_ADDITION) {
    override fun apply(values: DoubleArray): Double {
        return values[0] * values[1]
    }
}


private fun calculate(expr: String): Long {
    // println(expr.replace("*", "!"))
    val result = ExpressionBuilder(expr.replace("*", "!")).operator(specialMul).build().evaluate()
    // println(result)
    return result.toLong()
}

private fun part1(res: List<Long>, operands: List<List<Int>>): List<Long> {
    return res.filterIndexed { idx, result ->
        val opNums = operands[idx]
        val opArray = (1..opNums.size-1).map{ _ -> listOf("+", "*") }.toTypedArray()
        val ops = cartesianProduct(*opArray)
        var isValid = false

        ops.forEachIndexed { idx1, op ->
            if (!isValid) {
                val expr = mutableListOf<String>()
                opNums.forEachIndexed { idx2, opNum ->
                    if (idx2 < opNums.size - 1) {
                        expr.plusAssign(opNum.toString())
                        expr.plusAssign(ops[idx1][idx2])
                    }
                }
                expr.plusAssign(opNums.last().toString())
                val expr1 = (expr.joinToString(""))
                if (calculate(expr1) == result) {
                    isValid = true
                }
            }
        }

        return@filterIndexed isValid
    }
}