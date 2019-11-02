fun main() {
    var size = 0
    val ids = mutableSetOf<Int>()
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
            ids.add(id)
            Cloth(id, left, top, width, height)
        }

    val area = Array(size) { Array(size) { mutableSetOf<Int>() } }

    cloths.forEach { cloth ->
        for(x in cloth.left until (cloth.left + cloth.width)) {
            for (y in cloth.top until (cloth.top + cloth.height)) {
                area[x][y].add(cloth.id)
            }
        }
    }

    // Set of cloth ids that overlap with another cloth
    area.forEach { row ->
        row.forEach { col ->
            if(col.size > 1) {
                ids.removeAll(col)
            }
        }
    }

    println("Cloth that does not overlap is $ids")

}