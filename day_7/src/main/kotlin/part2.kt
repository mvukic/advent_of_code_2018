import asFile

fun main(args: Array<String>) {
    val steps = ClassLoader.getSystemClassLoader().getResource("Day7.txt").file
        .asFile()
        .readLines()
        .map {
            Step(it.split(" ")[1], it.split(" ")[7])
        }

    val names = steps.flatMap { listOf(it.name, it.parent) }.toSet().sorted().toList()

    val input = steps.map { it.name to it.parent }.toList()


}