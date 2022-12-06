fun main() {
    val data = utils.loadFile("Day6")

    val day = Day6()
    println(day.part1(data))
    println(day.part2(data))
}

class Day6 {
    fun part1(data: List<String>): Int {
        var index = 0
        while (index + 4 < data[0].count()) {
            val possibleMarker = data[0].substring(index, index + 4)
            if(allDifferent(possibleMarker)) return index + 4
            index++
        }

        return index
    }

    private fun allDifferent(chars: String): Boolean {
        return chars.all { c -> chars.count { it == c } == 1 }
    }

    fun part2(data: List<String>): Int {
        var index = 0
        while (index + 14 < data[0].count()) {
            val possibleMarker = data[0].substring(index, index + 14)
            if(allDifferent(possibleMarker)) return index + 14
            index++
        }

        return index
    }
}
