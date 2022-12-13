import java.math.BigInteger

fun main() {
    val data = utils.loadFile("Day11")

    val day = Day11()
    println(day.part1(data))
    println(day.part2(data))
}

class Day11 {
    data class Monkey(
        val number: Int,
        val items: ArrayDeque<BigInteger>,
        val test: BigInteger,
        val trueAction: Int,
        val falseAction: Int
    ) {
        var itemsInspected = 0
        private var opFirst: BigInteger? = null
        private var opLast: BigInteger? = null
        private var opSign: String = "*"

        fun setOperation(operation: String) {
            val parts = operation.split(" ")
            opFirst = if (parts.first() == "old") null else parts.first().toBigInteger()
            opLast = if (parts.last() == "old") null else parts.last().toBigInteger()
            opSign = parts[1]
        }

        fun inspect(old: BigInteger): BigInteger {
            itemsInspected++

            when (opSign) {
                "+" -> return (opFirst ?: old) + (opLast ?: old)
                "-" -> return (opFirst ?: old) - (opLast ?: old)
                "*" -> return (opFirst ?: old) * (opLast ?: old)
                "/" -> return (opFirst ?: old) / (opLast ?: old)
            }

            return BigInteger.ZERO
        }
    }

    private val BigThree = 3.toBigInteger()

    fun part1(data: List<String>): Int {
        val mutableList = data.toMutableList()
        mutableList.removeAll { it == "" }
        val textMonkies = mutableList.chunked(6)
        val monkies = mutableListOf<Monkey>()

        for (monkey in textMonkies) {
            val number = monkeyNumber(monkey.single { it.trim().startsWith("Monkey") })
            val items = monkeyItems(monkey.single { it.trim().contains("items") })
            val operation = monkeyOperation(monkey.single { it.trim().contains("Operation") })
            val test = monkeyTest(monkey.single { it.trim().contains("Test") })
            val trueAction = monkeyTrueFalse(monkey.single { it.trim().contains("true") })
            val falseAction = monkeyTrueFalse(monkey.single { it.trim().contains("false") })

            val m = Monkey(number, items, test, trueAction, falseAction)
            m.setOperation(operation)
            monkies.add(m)
        }

        for (round in 1..20) {
            for (monkey in monkies) {
                while (monkey.items.any()) {
                    val currentItem = monkey.items.removeFirst()
                    var worry = monkey.inspect(currentItem)
                    worry /= BigThree

                    if (worry.mod(monkey.test) == BigInteger.ZERO) {
                        monkies.single { it.number == monkey.trueAction }.items.addLast(worry)
                    } else {
                        monkies.single { it.number == monkey.falseAction }.items.addLast(worry)
                    }
                }
            }
        }

        monkies.sortBy { it.itemsInspected }

        return monkies[monkies.count() - 1].itemsInspected * monkies[monkies.count() - 2].itemsInspected
    }

    private fun monkeyNumber(line: String): Int = line.split(" ").last().removeSuffix(":").toInt()
    private fun monkeyItems(line: String): ArrayDeque<BigInteger> =
        ArrayDeque(line.split(":").last().split(",").map { it.trim().toBigInteger() })

    private fun monkeyOperation(line: String): String = line.split("=").last().trim()
    private fun monkeyTest(line: String): BigInteger = line.split(" ").last().toBigInteger()
    private fun monkeyTrueFalse(line: String): Int = line.split(" ").last().toInt()

    fun part2(data: List<String>): BigInteger {
        val mutableList = data.toMutableList()
        mutableList.removeAll { it == "" }
        val textMonkies = mutableList.chunked(6)
        val monkies = mutableListOf<Monkey>()

        var mod = BigInteger.ONE

        for (monkey in textMonkies) {
            val number = monkeyNumber(monkey.single { it.trim().startsWith("Monkey") })
            val items = monkeyItems(monkey.single { it.trim().contains("items") })
            val operation = monkeyOperation(monkey.single { it.trim().contains("Operation") })
            val test = monkeyTest(monkey.single { it.trim().contains("Test") })
            val trueAction = monkeyTrueFalse(monkey.single { it.trim().contains("true") })
            val falseAction = monkeyTrueFalse(monkey.single { it.trim().contains("false") })

            val m = Monkey(number, items, test, trueAction, falseAction)
            m.setOperation(operation)
            monkies.add(m)
        }

        mod = monkies.fold(BigInteger.ONE) {acc, monkey -> acc * monkey.test}

        for (round in 1..10000) {
            println("Round: $round")
            for (monkey in monkies) {
                while (monkey.items.any()) {
                    val currentItem = monkey.items.removeFirst()
                    var worry = monkey.inspect(currentItem)
                    //worry /= 3

                    worry %= mod

                    if (worry.mod(monkey.test) == BigInteger.ZERO) {
                        monkies.single { it.number == monkey.trueAction }.items.addLast(worry)
                    } else {
                        monkies.single { it.number == monkey.falseAction }.items.addLast(worry)
                    }
                }
            }
        }

        monkies.sortBy { it.itemsInspected }

        return monkies[monkies.count() - 1].itemsInspected.toBigInteger() * monkies[monkies.count() - 2].itemsInspected.toBigInteger()
    }
}
