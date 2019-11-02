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

    val grid = Array(maxX + 1) { Array(maxY + 1) { mutableListOf<Int>() }}

    // Check for every coordinate for the closest point
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            val minDistance = points.map { (x-it.first).absoluteValue + (y-it.second).absoluteValue }.min()!!
            points.forEachIndexed { index, pair ->
                if ((x-pair.first).absoluteValue + (y-pair.second).absoluteValue == minDistance) {
                    grid[x][y].add(index)
                }
            }
        }
    }

    // Find points closest to the edge
    val skip = mutableSetOf<Int>()
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            if (x == maxX || y == maxY || y == 0 || x == 0) {
                val closest = grid[x][y]
                if (closest.size == 1) {
                    skip.add(closest.first())
                }
            }
        }
    }

    val closest = mutableMapOf<Int, Int>()
    points.forEachIndexed { index, _ -> closest[index] = 0 }

    // get only points that are not in skip set
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            // only one closest point
            if(grid[x][y].size == 1) {
                // That closest point is not edge point
                if(!skip.contains(grid[x][y].first())) {
                    // Increase number of points in neighbourhood
                    closest[grid[x][y].first()] = closest.getValue(grid[x][y].first()) + 1
                }
            }
        }
    }

    closest.filter { it.value > 0 }.map { it.toPair() }.sortedByDescending { it.second }.map { Pair(points[it.first], it.second) }.forEach {
        println(it)
    }


}
