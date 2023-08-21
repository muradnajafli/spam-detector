package processor

/**
 * Class that will split text into several parts.
 * `chunkSize` represents how many lines can be in single part (chunk) of the text.
 * Each part could not always contain the number of lines equal to chunk size.
 */
class LetterProcessor(private val chunkSize: Int = CHUNK_SIZE) {

    /**
     * Method split text into parts. The size of each part is defined by `chunkSize` from the constructor.
     * @param content text to split into parts
     * @return list of parts of the original text.
     */
    fun splitLetter(content: String): List<String> {
        val lines = content.lines()
        return lines.chunked(chunkSize) { it.joinToString("\r\n") }
    }

    companion object {
        const val CHUNK_SIZE = 2
    }
}