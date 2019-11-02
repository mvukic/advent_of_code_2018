fun main(args: Array<String>) {
    var sum = 0
    ClassLoader.getSystemClassLoader().getResource("Day1.txt").file
        .asFile()
        .readLines()
        .map {
            Pair(it[0], it.substring(1).toInt())
        }
        .forEach {
            if (it.first == '+') {
                sum += it.second
            } else {
                sum -= it.second
            }
        }
    println("Sum is $sum")
}