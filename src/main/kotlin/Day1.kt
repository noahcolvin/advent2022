fun main() {
    val data = utils.loadFile("Day1")

    val day = Day1()
    println(day.part1(data))
    println(day.part2(data))
}

class Day1 {
    fun part1(data: List<String>): Int {
        var currentCalories = 0
        var fattestCalories = 0

        for (line in data) {
            if (line == "") {
                if (currentCalories > fattestCalories) {
                    fattestCalories = currentCalories
                }

                currentCalories = 0
                continue
            }

            val calories = line.toInt()
            currentCalories += calories
        }

        return fattestCalories
    }

    fun part2(data: List<String>): Int {
        val topCalories = mutableListOf(0, 0, 0)
        var currentCalories = 0

        for (line in data) {
            if (line == "") {
                if (currentCalories > topCalories.min()) {
                    topCalories.remove(topCalories.min())
                    topCalories.add(currentCalories)
                }

                currentCalories = 0
                continue
            }

            val calories = line.toInt()
            currentCalories += calories
        }

        return topCalories.sum()
    }
}