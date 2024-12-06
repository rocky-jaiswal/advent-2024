package dev.rockyj.advent24

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
}
