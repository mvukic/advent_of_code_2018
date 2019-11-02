fun main() {

    val line = ClassLoader.getSystemClassLoader().getResource("Day5.txt").file
        .asFile()
        .readLines()
        .asSequence()
        .first()
        .trim()
        .toCharArray()

    val map = mutableMapOf<Char, Pair<Int, String>>()
    ('a'..'z').forEach { unit ->
        val stack = mutableListOf<Char>()
        line.filter { it.toLowerCase() != unit }
            .forEach {
            stack.add(it)
            if(stack.size > 1) {
                val first = stack[stack.size - 2]
                val second = stack[stack.size - 1]
                if (first.toLowerCase() == second.toLowerCase() && (first.isLowerCase() && second.isUpperCase() || first.isUpperCase() && second.isLowerCase())) {
                    stack.removeAt(stack.size - 1)
                    stack.removeAt(stack.size - 1)
                }
            }
        }
        map[unit] = Pair(stack.size, stack.joinToString(""))
    }

    println("Unit -> Size, Result")
    println("=".repeat(20))
    map.entries.sortedBy { it.value.first }.forEach {
        println("${it.key} -> ${it.value}")
    }
}
