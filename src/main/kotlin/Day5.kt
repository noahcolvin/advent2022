fun main() {
    val data = utils.loadFile("Day5")

    val day = Day5()
    println(day.part1(data))
    println(day.part2(data))
}

class Day5 {
    data class Move(val count: Int, val from: Int, val to: Int)

    fun part1(data: List<String>): String {
        val stacks = getStacks(data)
        val moves = getMoves(data)

        for (move in moves) {
            for (num in 0 until move.count) {
                val popped = stacks[move.from - 1].removeFirst()
                stacks[move.to - 1].addFirst(popped)
            }
        }

        var result = ""
        for (stack in stacks) {
            result += stack.first()
        }

        return result
    }

    private fun getStacks(data: List<String>): List<ArrayDeque<String>> {
        val stacks = mutableListOf<ArrayDeque<String>>()

        val emptyLineIndex = data.indexOf("")
        for (stack in data[emptyLineIndex - 2].split(" "))
            stacks.add(ArrayDeque())

        for (line in data.subList(0, emptyLineIndex - 1)) {
            val chunks = line.chunked(4).map { it.trim().removeSurrounding("[", "]") }
            for (chunk in 0 until chunks.count()) {
                if (chunks[chunk] != "")
                    stacks[chunk].add(chunks[chunk])
            }
        }

        return stacks
    }

    fun parseMap(input: List<String>): List<MutableList<Char>> {
        val map = input.takeWhile { it.isNotBlank() }.reversed()
        val count = map.first().replace(" ", "").last().digitToInt()
        val stacks: List<MutableList<Char>> = buildList(count) { for (i in 0 until count) add(mutableListOf()) }

        map.drop(1).forEach { line ->
            for (i in 0 until count) {
                val index = 1 + i * 4
                line.getOrNull(index)?.let {
                    stacks[i].add(it)
                }
            }
        }

        return stacks
    }

    private fun getMoves(data: List<String>): List<Move> {
        val moves = mutableListOf<Move>()
        val emptyLineIndex = data.indexOf("")

        for (line in data.subList(emptyLineIndex + 1, data.count())) {
            val parts = line.split(" ").filter { it.first().isDigit() }.map { it.toInt() }
            moves.add(Move(count = parts[0], from = parts[1], to = parts[2]))
        }
        return moves
    }

    fun part2(data: List<String>): String {
        val stacks = getStacks(data)
        val moves = getMoves(data)

        for (move in moves) {
            val group = mutableListOf<String>()
            for (num in 0 until move.count) {
                val popped = stacks[move.from - 1].removeFirst()
                group.add(popped)
            }

            for (item in group.reversed()) {
                stacks[move.to - 1].addFirst(item)
            }
        }

        var result = ""
        for (stack in stacks) {
            result += stack.first()
        }

        return result
    }
}
