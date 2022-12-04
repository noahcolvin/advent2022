fun main() {
    val data = utils.loadFile("Day4")

    val day = Day4()
    println(day.part1(data))
    println(day.part2(data))
}

class Day4 {
    fun part1(data: List<String>): Int {
        var total = 0

        for (line in data) {
            val groups = line.split(",")
            val group1 = groups[0].split("-").map { it.toInt() }
            val group2 = groups[1].split("-").map { it.toInt() }

            val range1 = group1[0]..group1[1]
            val range2 = group2[0]..group2[1]

            if (range1.all { range2.contains(it) } || range2.all { range1.contains(it) }) {
                total++
            }
        }
        return total
    }

    fun part2(data: List<String>): Int {
        var total = 0

        for (line in data) {
            val groups = line.split(",")
            val group1 = groups[0].split("-").map { it.toInt() }
            val group2 = groups[1].split("-").map { it.toInt() }

            val range1 = group1[0]..group1[1]
            val range2 = group2[0]..group2[1]

            if (range1.any { range2.contains(it) } || range2.any { range1.contains(it) }) {
                total++
            }
        }
        return total
    }
}
