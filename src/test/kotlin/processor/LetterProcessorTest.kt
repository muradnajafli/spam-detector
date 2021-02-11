package processor

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LetterProcessorTest {

    private lateinit var processor: LetterProcessor

    @Before
    fun setup() {
        processor = LetterProcessor()
    }

    @Test
    fun process_empty_letter() {
        val result = processor.splitLetter("")
        assertEquals(listOf(""), result)
    }

    @Test
    fun process_single_chunk_letter() {
        val firstLine = "Line 1"
        val secondLine = "Line 2"
        val expectedChunk = "$firstLine${System.lineSeparator()}$secondLine"
        val result = processor.splitLetter("$firstLine\n$secondLine")
        assertEquals(listOf(expectedChunk), result)
    }

    @Test
    fun process_multiple_chunks_letter() {
        val firstLine = "Line 1"
        val secondLine = "Line 2"
        val thirdLine = "Line 3"
        val expectedFirstChunk = "$firstLine${System.lineSeparator()}$secondLine"
        val result = processor.splitLetter("$firstLine\n$secondLine\n$thirdLine")
        assertEquals(listOf(expectedFirstChunk, thirdLine), result)
    }

}