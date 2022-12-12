fun main() {
    val data = utils.loadFile("Day10")

    val day = Day10()
    println(day.part1(data))
    println(day.part2(data))
}

class Day10 {
    fun part1(data: List<String>): Int {
        var total = 0
        var cycle = 0
        var X = 1

        for (inst in data) {
            if (inst == "noop") {
                cycle++
                total += checkCycle(cycle, X)
            } else {
                val value = inst.split(' ').last().toInt()
                cycle++
                total += checkCycle(cycle, X)
                cycle++
                total += checkCycle(cycle, X)
                X += value
            }
        }

        return total
    }

    private fun checkCycle(cycle: Int, X: Int): Int {
        when (cycle) {
            20, 60, 100, 140, 180, 220 -> {
                println("$cycle -> ${cycle * X}")
                return cycle * X
            }
        }

        return 0
    }

    private fun drawPixel(line: String, cycle: Int, X: Int): String {
        if (cycle == X - 1 || cycle == X || cycle == X + 1) {
            return "$line#"
        }
        return return "$line."
    }

    fun part2(data: List<String>): Int {
        var cycle = 0
        var X = 1
        var line = ""

        fun checkLine() {
            if (cycle == 40) {
                println(line)
                line = ""
                cycle = 0
            }
        }

        for (inst in data) {
            if (inst == "noop") {
                line = drawPixel(line, cycle, X)
                cycle++
                checkLine()
            } else {
                val value = inst.split(' ').last().toInt()
                line = drawPixel(line, cycle, X)
                cycle++
                checkLine()
                line = drawPixel(line, cycle, X)
                cycle++
                checkLine()
                X += value
            }
        }

        return 0
    }
}
