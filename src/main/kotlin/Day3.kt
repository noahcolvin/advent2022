fun main() {
    val data = utils.loadFile("Day3")

    val day = Day3()
    println(day.part1(data))
    println(day.part2(data))
}

class Day3 {
    fun part1(data: List<String>): Int {
        var total = 0

        for (line in data) {
            val one = line.substring(0, line.length / 2)
            val two = line.substring(line.length / 2)

            val same = one.findAnyOf(two.toCharArray().map { it.toString() })?.second!!
            total += charValue(same[0])
        }

        return total
    }

    private fun charValue(char: Char): Int {
        if (char.isLowerCase()) {
            return char.code - 97 + 1 //ascii to starting 1
        }

        return char.code - 65 + 27 //ascii to starting 27
    }

    fun part2(data: List<String>): Int {
        var total = 0
        val chunks = data.chunked(3)

        for (chunk in chunks) {
            val sameFirstTwo = chunk[0].filter { chunk[1].contains(it) }
            val sameThree = sameFirstTwo.filter { chunk[2].contains(it) }
            val sameAll = sameThree.toCharArray().distinct().single()

            total += charValue(sameAll)
        }

        return total
    }
}
