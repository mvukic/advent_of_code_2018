fun main() {

    val ids = ClassLoader.getSystemClassLoader().getResource("Day2.txt").file
        .asFile()
        .readLines()

    var exactlyTwo = 0
    var exactlyThree = 0

    ids.forEach {
        val map = mutableMapOf<Char, Int>()
        it.forEach { char ->
            if (map.containsKey(char)) {
                map[char] = map[char]!! + 1
            } else {
                map[char] = 1
            }
        }
        val onlyTwo = map.values.contains(2)
        val onlyThree = map.values.contains(3)
        if (onlyTwo) {
            exactlyTwo += 1
        }
        if (onlyThree) {
            exactlyThree += 1
        }
    }
    println(exactlyThree)
    println(exactlyTwo)
    println(exactlyThree*exactlyTwo)
}