fun main() {
    val data = utils.loadFile("Day8")

    val day = Day8()
    println(day.part1(data))
    println(day.part2(data))
}

class Day8 {
    fun part1(data: List<String>): Int {
        val visible = mutableListOf<String>()

        for (x in 0 until data.first().count()) {
            for (y in 0 until data.first().count()) {
                val coords = "${x},${y}"
                if (!visible.contains(coords) && isVisible(data, x, y)) {
                    visible.add(coords)
                }
            }
        }
        return visible.count()
    }

    private fun isVisible(data: List<String>, x: Int, y: Int): Boolean {
        if (x == 0 || y == 0 || x == data.first().count() - 1 || y == data.count() - 1)
            return true

        if (isVisibleRow(data, x, y))
            return true

        if (isVisibleColumn(data, x, y))
            return true

        return false
    }

    private fun isVisibleRow(data: List<String>, x: Int, y: Int): Boolean {
        val row = data[x]
        val height = row[y]
        var visibleLeft = true

        for (pos in 0 until y) {
            if (row[pos] >= height) visibleLeft = false
        }

        if (!visibleLeft) {
            for (pos in y + 1 until row.length) {
                if (row[pos] >= height) return false
            }
        }

        return true
    }

    private fun isVisibleColumn(data: List<String>, x: Int, y: Int): Boolean {
        val height = data[x][y]
        var visibleTop = true

        for (pos in 0 until x) {
            if (data[pos][y] >= height)
                visibleTop = false
        }

        if (!visibleTop) {
            for (pos in x + 1 until data.count()) {
                if (data[pos][y] >= height)
                    return false
            }
        }

        return true
    }

    fun part2(data: List<String>): Int {
        var largest = 0

        for (row in 0 until data.count()) {
            for (column in 0 until data.first().count()) {
                val score = scenicScore(data, column, row)
                if(score > largest) {
                    largest = score
                }
            }
        }
        return largest
    }

    private fun scenicScore(data: List<String>, column: Int, row: Int): Int {
        return scenicScoreRow(data, column, row) * scenicScoreColumn(data, column, row)
    }

    private fun scenicScoreRow(data: List<String>, column: Int, row: Int): Int {
        val foundRow = data[row]
        val height = foundRow[column].toString().toInt()

        var scoreLeft = 0
        for (pos in column - 1 downTo 0) {
            if (foundRow[pos].toString().toInt() < height)
                scoreLeft++
            else if (foundRow[pos].toString().toInt() >= height) {
                scoreLeft++
                break
            }
        }

        var scoreRight = 0
        for (pos in column + 1 until foundRow.length) {
            if (foundRow[pos].toString().toInt() < height)
                scoreRight++
            else if (foundRow[pos].toString().toInt() >= height) {
                scoreRight++
                break
            }
        }

        return scoreLeft * scoreRight
    }

    private fun scenicScoreColumn(data: List<String>, column: Int, row: Int): Int {
        val height = data[row][column].toString().toInt()

        var scoreUp = 0
        for (pos in row - 1 downTo 0) {
            val value = data[pos][column].toString().toInt()
            if (value < height) {
                scoreUp++
            } else if (value >= height) {
                scoreUp++
                break
            }
        }

        var scoreDown = 0
        for (pos in row + 1 until data.count()) {
            val value = data[pos][column].toString().toInt()
            if (value < height) {
                scoreDown++
            } else if (value >= height) {
                scoreDown++
                break
            }
        }

        return scoreUp * scoreDown
    }
}
