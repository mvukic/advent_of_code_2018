val initialStateRegex = "^initial state: ([.#]+)$".toRegex()
val ruleRegex = "^([.#]+) => ([.#]+)$".toRegex()

fun main() {
  val lines = getLines("input.txt")
  val pots = getInitialState(lines)
  println("Initial state: $pots\n")
  val rules = getRules(lines)

  var currentPots = pots.map { it }
  (1..2).forEach { generation ->
    println("Current plants: ${currentPots.joinToString("")} ($generation)")
    val newPlants = currentPots.map { it }.toMutableList()
    currentPots.forEachIndexed { index, _ ->
      // Get indexes of the whole pattern
      val indexes = listOf(index - 2, index - 1, index, index + 1, index + 2)
      // Calculate the whole patters surrounding this pot
      val pattern = getPattern(indexes, currentPots)
      // Find rules that have this patters as a condition
      val rule = rules.find { it.condition === pattern }
      if (rule !== null) {
        // Get rule result and replace the old pot value with the new one
        newPlants[index] = rule.result
      }
    }
    currentPots = newPlants.map { it }.toMutableList()
  }
}

fun getPattern(indexes: List<Int>, pots: List<String>): String {
  return indexes.joinToString("") { index ->
    pots.getOrElse(index) { "." }
  }
}

fun getInitialState(lines: List<String>): List<String> {
  val line = lines.first()
  val groups = initialStateRegex.find(line)
  if (groups !== null) {
    val members = groups.destructured.toList()
    return members[0].toCharArray().map { it.toString() }
  }
  return listOf()
}

fun getRules(lines: List<String>): List<Rule> {
  return lines
    .drop(2)
    .mapNotNull { ruleRegex.find(it) }
    .map {
      val members = it.destructured.toList()
      Rule(
        center = members[0][2].toString(),
        condition = members[0],
        result = members[1]
      )
    }
}

data class Rule(
  val center: String,
  val condition: String,
  val result: String
) {
}