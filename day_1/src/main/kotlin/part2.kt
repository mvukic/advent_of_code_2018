fun main(args: Array<String>) {
    val set = HashSet<Int>()
    var sum = 0

    val pairs = ClassLoader.getSystemClassLoader().getResource("Day1.txt").file
        .asFile()
        .readLines()
        .map {
            Pair(it[0], it.substring(1).toInt())
        }
    var i = 0
    set.add(0)
    while(true) {
        val pair = pairs[i]
        val newSum = fix(sum, pair.second, pair.first)
        if (set.contains(newSum)) {
            println(newSum)
            break;
        } else {
            sum = newSum
            set.add(newSum)
        }
        i++
        if (i == pairs.size) {
            i = 0
        }
    }
}

fun fix(sum: Int, value: Int, op: Char): Int {
    if (op == '+') {
        return sum + value
    } else {
        return sum - value
    }
}