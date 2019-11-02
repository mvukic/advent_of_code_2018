import asFile
import kotlin.math.absoluteValue

fun main(args: Array<String>) {

    val points = ClassLoader.getSystemClassLoader().getResource("Day6.txt").file
        .asFile()
        .readLines()
        .map {
            Pair(it.split(",")[0].trim().toInt(), it.split(",")[1].trim().toInt())
        }

    // find max x and y coordinate
    val maxX = points.map { it.first }.max()!!
    val maxY = points.map { it.second }.max()!!

    val limit = 10000

    // Check for every coordinate for the closest point
    var counter = 0
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            var sum = 0
            points.forEach{ pair ->
                sum += (x-pair.first).absoluteValue + (y-pair.second).absoluteValue
            }
            if (sum < limit) {
                counter++
            }
        }
    }
    println(counter)
}
