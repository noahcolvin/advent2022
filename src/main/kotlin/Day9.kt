fun main() {
    val data = utils.loadFile("Day9")

    val day = Day9()
    println(day.part1(data))
    println(day.part2(data))
}

class Day9 {
    private data class Knot(var column: Int = 0, var row: Int = 0)

    fun part1(data: List<String>): Int {
        val tailMoves = mutableListOf<String>()
        val head = Knot()
        val tail = Knot()
        tailMoved(tailMoves, tail)

        for (line in data) {
            val move = line.split(" ")
            for (x in 1..move[1].toInt()) {
                when (move[0]) {
                    "U" -> {
                        head.row++
                        if (tail.column == head.column && tail.row + 1 < head.row) {
                            //head gets too high
                            tail.row++
                            tailMoved(tailMoves, tail)
                        } else if (tail.column != head.column && tail.row + 1 < head.row) {
                            //head gets too high diagonally
                            tail.row++
                            tail.column = head.column
                            tailMoved(tailMoves, tail)
                        }
                    }

                    "D" -> {
                        head.row--
                        if (tail.column == head.column && tail.row - 1 > head.row) {
                            //head gets too low
                            tail.row--
                            tailMoved(tailMoves, tail)
                        } else if (tail.column != head.column && tail.row - 1 > head.row) {
                            //head gets too low diagonally
                            tail.row--
                            tail.column = head.column
                            tailMoved(tailMoves, tail)
                        }
                    }

                    "R" -> {
                        head.column++
                        if (tail.row == head.row && tail.column + 1 < head.column) {
                            tail.column++
                            tailMoved(tailMoves, tail)
                        } else if (tail.row != head.row && tail.column + 1 < head.column) {
                            tail.column++
                            tail.row = head.row
                            tailMoved(tailMoves, tail)
                        }
                    }

                    "L" -> {
                        head.column--
                        if (tail.row == head.row && tail.column - 1 > head.column) {
                            tail.column--
                            tailMoved(tailMoves, tail)
                        } else if (tail.row != head.row && tail.column - 1 > head.column) {
                            tail.column--
                            tail.row = head.row
                            tailMoved(tailMoves, tail)
                        }
                    }
                }
            }
        }

        return tailMoves.count()
    }

    private fun whip(knots: List<Knot>, tailMoves: MutableList<String>) {
        var last: Knot? = null

        for (knot in knots) {
            if (last == null) {
                last = knot
                continue
            }

            if (knot.column == last.column && knot.row + 1 < last.row) {
                knot.row++
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.column != last.column && knot.row + 1 < last.row) {
                knot.row++
                if (last.column > knot.column) knot.column++ else knot.column--
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.column == last.column && knot.row - 1 > last.row) {
                knot.row--
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.column != last.column && knot.row - 1 > last.row) {
                knot.row--
                if (last.column > knot.column) knot.column++ else knot.column--
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.row == last.row && knot.column + 1 < last.column) {
                knot.column++
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.row != last.row && knot.column + 1 < last.column) {
                knot.column++
                if (last.row > knot.row) knot.row++ else knot.row--
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.row == last.row && knot.column - 1 > last.column) {
                knot.column--
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            } else if (knot.row != last.row && knot.column - 1 > last.column) {
                knot.column--
                if (last.row > knot.row) knot.row++ else knot.row--
                if (knots.last() == knot) tailMoved(tailMoves, knot)
            }

            last = knot
        }
    }

    private fun tailMoved(tailMoves: MutableList<String>, tail: Knot) {
        val move = "(${tail.column}, ${tail.row})"
        if (!tailMoves.contains(move))
            tailMoves.add(move)
    }

    fun part2(data: List<String>): Int {
        val knots = listOf(Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot(), Knot())
        val tailMoves = mutableListOf<String>()
        tailMoved(tailMoves, knots.last())

        for (line in data) {
            val move = line.split(" ")
            for (x in 1..move[1].toInt()) {
                when (move[0]) {
                    "U" -> {
                        knots.first().row++
                    }

                    "D" -> {
                        knots.first().row--
                    }

                    "R" -> {
                        knots.first().column++
                    }

                    "L" -> {
                        knots.first().column--
                    }
                }

                whip(knots, tailMoves)
            }
        }

        return tailMoves.count()
    }
}
