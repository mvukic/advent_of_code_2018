fun main() {
    val stack = mutableListOf<Char>()
    ClassLoader.getSystemClassLoader().getResource("Day5.txt").file
        .asFile()
        .readLines()
        .asSequence()
        .first()
        .trim()
        .toCharArray()
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
    println("Result size: ${stack.size}")
}
