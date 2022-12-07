fun main() {
    val data = utils.loadFile("Day7")

    val day = Day7()
    println(day.part1(data))
    println(day.part2(data))
}

class Day7 {
    data class File(val name: String, val size: Int)
    data class Directory(
        val name: String,
        val parent: Directory?,
        val directories: MutableList<Directory> = mutableListOf(),
        val files: MutableList<File> = mutableListOf()
    ) {
        fun size(): Int {
            var subDirSize = directories.sumOf { it.size() }
            var fileSize = files.sumOf { it.size }
            return subDirSize + fileSize
        }
    }

    fun part1(data: List<String>): String {
        val tree = buildDirectories(data)
        val rightSize = directoriesBelowSize(tree, 100000)

        return rightSize.sumOf { it.size() }.toString()
    }

    private fun directoriesBelowSize(directory: Directory, size: Int): MutableList<Directory> {
        val dirs = mutableListOf<Directory>()

        if (directory.size() <= size)
            dirs.add(directory)

        directory.directories.forEach { dir ->
            directoriesBelowSize(dir, size).forEach { dirs.add(it) }
        }

        return dirs
    }

    private fun buildDirectories(data: List<String>): Directory {
        val root = Directory("/", null)
        var currentDirectory: Directory = root
        var currentLine = -1

        while (++currentLine < data.count()) {
            val line = data[currentLine]
            val parts = line.split(" ")

            if (parts[0] == "$") {
                when (parts[1]) {
                    "cd" -> {
                        if (currentDirectory.name == parts[2])
                            continue

                        if (parts[2] == "..") {
                            currentDirectory = currentDirectory.parent ?: root
                            continue
                        }

                        var dir = currentDirectory.directories.find { it.name == parts[2] }
                        if (dir == null) {
                            dir = Directory(parts[2], currentDirectory)
                            currentDirectory.directories.add(dir)
                        }

                        currentDirectory = dir
                    }

                    "ls" -> {
                        while (currentLine + 1 < data.count() && !data[currentLine + 1].startsWith("$")) {
                            currentLine++
                            val listParts = data[currentLine].split(" ")

                            if (listParts[0] == "dir")
                                continue

                            val currentFile = currentDirectory.files.find { it.name == listParts[1] }
                            if (currentFile == null) {
                                currentDirectory.files.add(File(listParts[1], listParts[0].toInt()))
                            }
                        }
                    }
                }
            }
        }

        return root
    }

    private fun directoriesAboveSize(directory: Directory, size: Int): MutableList<Directory> {
        val dirs = mutableListOf<Directory>()

        if (directory.size() > size)
            dirs.add(directory)

        directory.directories.forEach { dir ->
            directoriesAboveSize(dir, size).forEach { dirs.add(it) }
        }

        return dirs
    }

    fun part2(data: List<String>): String {
        val tree = buildDirectories(data)
        val total = 70000000
        val freeSpace = total - tree.size()
        val toFree = 30000000 - freeSpace

        val rightSize = directoriesAboveSize(tree, toFree)

        return rightSize.minOf { it.size() }.toString()
    }
}
