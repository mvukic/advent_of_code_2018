fun main() {
    val lines = ClassLoader.getSystemClassLoader().getResource("Day4.txt").file
        .asFile()
        .readLines()
        .map { line ->
            val data = line.split("[", "]", " ", "-", ":").map { it.trim() }.filter { it.isNotEmpty() }
            val record = Record(
                data[0].toInt(),
                data[1].toInt(),
                data[2].toInt(),
                data[3].toInt(),
                data[4].toInt()
            )
            when {
                data[5] == "wakes" -> record.state = GuardState.AWAKE
                data[5] == "falls" -> record.state = GuardState.ASLEEP
                else -> {
                    when {
                        data[7] == "begins" -> record.state = GuardState.START_SHIFT
                    }
                    record.guard = data[6].substring(1)
                }
            }
            record
        }
    // Sort records by date
    val sorted = lines.sortedWith(compareBy({it.year}, {it.month}, {it.day}, {it.hour}, {it.minute}))
    val duties = mutableMapOf<String, Guard>()
    // Fix guards for every record
    var lastGuardInRecord = sorted.first().guard
    sorted.forEach {
        if(it.guard.isEmpty()) it.guard = lastGuardInRecord
        else lastGuardInRecord = it.guard
    }
    println("Date\tID\tMinute")
    print("    \t  \t")
    for(i in 0 until 6) print("$i$i$i$i$i$i$i$i$i$i")
    println()
    print("    \t  \t")
    for(i in 0 until 6) for(j in 0 until 10) print(j)
    println()
    sorted.filter { it.state != GuardState.START_SHIFT}.groupBy { "${it.month}-${it.day}" }
        .forEach { date, records ->
            val guard = records.map { it.guard }.first()
            val sleepByMinutes = mutableMapOf<Int, Boolean>()
            for(i in 0 until 60) sleepByMinutes[i] = false

            records.forEachIndexed { index, record ->
                if (record.state == GuardState.ASLEEP) {
                    if (index == 0) {
                        for(i in 0 until record.minute) sleepByMinutes[i] = false
                        sleepByMinutes[record.minute] = true
                    } else {
                        val prev = records[index-1]
                        for (i in prev.minute until record.minute) sleepByMinutes[i] = false
                        sleepByMinutes[record.minute] = true
                    }
                } else if (record.state == GuardState.AWAKE) {
                    if (index == 0) {
                        for(i in 0 until record.minute) sleepByMinutes[i] = true
                        sleepByMinutes[record.minute] = false
                    } else {
                        val prev = records[index-1]
                        for (i in prev.minute until record.minute) sleepByMinutes[i] = true
                        sleepByMinutes[record.minute] = false
                    }
                }
            }
            print("$date\t#$guard\t")
            sleepByMinutes.entries.sortedBy { it.key }.forEach {
                if (it.value) print("X") else print(".")
            }
            println()
            // Current sleep duration
            duties.putIfAbsent(guard, Guard(guard.toInt()))
            duties.getValue(guard).duration += sleepByMinutes.values.count { it }
            sleepByMinutes.values.forEachIndexed { index, wasAsleep ->
                if (wasAsleep) {
                    duties.getValue(guard).minutes[index] += 1
                }
            }
        }

    val guards = duties.values
    val minutes = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
    for (i in 0 until 60) {
        minutes[i] = mutableListOf()
        guards.forEach {
            minutes.getValue(i).add(Pair(it.guard, it.minutes[i]))
        }
    }

    val result = mutableListOf<Triple<Int, Int ,Int>>()
    minutes.forEach { minute, pairs ->
        if(pairs.distinctBy { it.second }.size == 1) {
            result.add(Triple(minute, 0, 0))
        } else {
            val pair = pairs.maxBy { it.second }!!
            result.add(Triple(minute, pair.second, pair.first))
        }
    }
    val guard =  result.maxBy { it.second }!!
    println("Minute: ${guard.first} -> Sleeps: ${guard.second} Guard: ${guard.third}")
    println(guard.first * guard.third)
}
