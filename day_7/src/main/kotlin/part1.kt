import asFile

fun main(args: Array<String>) {
    val steps = ClassLoader.getSystemClassLoader().getResource("Day7.txt").file
        .asFile()
        .readLines()
        .map {
            Step(it.split(" ")[1], it.split(" ")[7])
        }

    val names = mutableSetOf<String>()
    steps.forEach {
        names.add(it.name)
        names.add(it.parent)
    }

    val dependencies = mutableListOf<Dependency>()
    names.forEach { name ->
        dependencies.add(Dependency(name, steps.filter { it.parent == name }.map { it.name }))
    }

    val reverseDependencies = mutableListOf<Dependency>()
    names.forEach { name ->
        reverseDependencies.add(Dependency(name, steps.filter { it.name == name }.map { it.parent }.sorted()))
    }

    // Array of active nodes (at the start only roots are active)
    val pastActive = mutableListOf<String>()
    var active = dependencies.filter { it.children.isEmpty() }.map { it.name }.sorted().toMutableSet()

    var atIndex = 0
    while(true) {
        // Break if every node is active
        if (pastActive.size == names.size) {
            break
        }
        println("Current active states: $active")
        // take first active
        val first  = active.toList()[atIndex]
        // Check if prerequisites for first are satisfied
        val parents = dependencies.find { it.name == first }!!.children
        if (!pastActive.containsAll(parents)) {
            println("\tState $first is not satisfied!!")
            atIndex++
            continue
        }
        println("\tState: $first")
        // get its children
        val children = reverseDependencies.find { it.name == first }!!.children
        println("\tAdding to active: $children")
        // remove that active from active list to pastActive list
        active.remove(first)
        println("\tRemoving from active: $first")
        // add children to activeList
        active.addAll(children)
        // Sort active list
        active = active.sorted().toMutableSet()
        pastActive.add(first)
        atIndex = 0
    }

    println(pastActive.joinToString(""))

}

data class Step(val name: String, val parent: String)
data class Dependency(val name: String, val children: List<String>)