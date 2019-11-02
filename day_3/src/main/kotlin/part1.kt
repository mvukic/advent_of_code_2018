fun main() {

    var size = 0

    val cloths: List<Cloth> = ClassLoader.getSystemClassLoader().getResource("Day3.txt").file
        .asFile()
        .readLines()
        .map {
            val list = it.split(" ".toRegex())
            val id = list[0].substring(1).toInt()
            val left = list[2].split(",")[0].toInt()
            val top = list[2].split(",", ":")[1].toInt()
            val width = list[3].split("x".toRegex())[0].toInt()
            val height = list[3].split("x".toRegex())[1].toInt()
            if (size < top + height) size = top + height
            if (size < left + width) size = left + width
            Cloth(id, left, top, width, height)
        }

    val area = Array(size) { Array(size) { 0 } }
    var overlapping = 0

    cloths.forEach { cloth ->
        for(x in cloth.left until (cloth.left + cloth.width)) {
            for (y in cloth.top until (cloth.top + cloth.height)) {
                area[x][y]++
            }
        }
    }

    area.forEach { row ->
        row.forEach {
            if(it > 1) overlapping++
        }
    }

    println("Overlapping coordinated: $overlapping")

}


data class Cloth(
    val id: Int,
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int
)