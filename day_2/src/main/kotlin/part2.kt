import kotlin.system.exitProcess

fun main() {

    val ids = ClassLoader.getSystemClassLoader().getResource("Day2.txt").file
        .asFile()
        .readLines()

    var i =0

    while(i < ids.size) {
        val current = ids[i]
        var j = i+1
        while(j < ids.size) {
            val next = ids[j]
            var difference = 0
            for (k in current.indices) {
                if(current[k] != next[k]) {
                    difference++
                }
            }
            if(difference == 1) {
                exit(current, next)
            }
            j++
        }
        i++
    }

}

fun exit(first: String, second: String) {
    println(first)
    println(second)
    exitProcess(0)
}