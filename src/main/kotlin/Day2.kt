fun main() {
    val data = utils.loadFile("Day2")

    val day = Day2()
    println(day.part1(data))
    println(day.part2(data))
}

class Day2 {
    private val moves = mapOf("A" to 1, "B" to 2, "C" to 3, "X" to 1, "Y" to 2, "Z" to 3)

    fun part1(data: List<String>): Int {
        var score = 0

        for (line in data) {
            val plays = line.split(" ")
            if (playOutcome(plays[0], plays[1]) == 0) { //you tie
                score += 3
            } else if (playOutcome(plays[0], plays[1]) == 1) { //you win
                score += 6
            }

            score += moves[plays[1]]!!
        }
        return score
    }

    private fun playOutcome(opponent: String, me: String): Int {
        // A, X == Rock
        // B, Y == Paper
        // C, Z == Scissors
        if (opponent == "A" && me == "Z") //Rock beats Scissors
            return -1
        if (opponent == "A" && me == "Y") //Rock loses to Paper
            return 1
        if (opponent == "B" && me == "X") //Paper beats Rock
            return -1
        if (opponent == "B" && me == "Z") //Paper loses to Scissors
            return 1
        if (opponent == "C" && me == "Y") //Scissors beats Paper
            return -1
        if (opponent == "C" && me == "X") //Scissors loses to Rock
            return 1

        return 0
    }

    fun part2(data: List<String>): Int {
        var score = 0

        for (line in data) {
            val plays = line.split(" ")
            if (plays[1] == "X") { //lose
                if (playOutcome(plays[0], "X") < 0) {
                    score += moves["X"]!!
                } else if (playOutcome(plays[0], "Y") < 0) {
                    score += moves["Y"]!!
                } else if (playOutcome(plays[0], "Z") < 0) {
                    score += moves["Z"]!!
                }
            }

            if (plays[1] == "Y") { //tie
                if (playOutcome(plays[0], "X") == 0) {
                    score += 3 + moves["X"]!!
                } else if (playOutcome(plays[0], "Y") == 0) {
                    score += 3 + moves["Y"]!!
                } else if (playOutcome(plays[0], "Z") == 0) {
                    score += 3 + moves["Z"]!!
                }
            }
            if (plays[1] == "Z") { //win
                if (playOutcome(plays[0], "X") > 0) {
                    score += 6 + moves["X"]!!
                } else if (playOutcome(plays[0], "Y") > 0) {
                    score += 6 + moves["Y"]!!
                } else if (playOutcome(plays[0], "Z") > 0) {
                    score += 6 + moves["Z"]!!
                }
            }
        }
        return score
    }
}
