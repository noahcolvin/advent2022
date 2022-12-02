class Utils {
    fun loadFileRaw(file: String): String {
        return this::class.java.getResource(file)?.readText() ?: ""
    }

    fun loadFile(file: String): List<String> {
        return loadFileRaw(file).split("\r\n")
    }
}

val utils = Utils()