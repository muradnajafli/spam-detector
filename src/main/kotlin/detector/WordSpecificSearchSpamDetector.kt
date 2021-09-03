package detector

import kotlinx.coroutines.*

/**
 * Word specific spam detector takes markers that is usually words from spam letter.
 * Default value is 'spam' and 'advertisement'.
 * Detection should be performed asynchronously in the scope provided in constructor.
 * Text is treated as a spam if there is at least one marker presents.
 * In case markers is empty throw IllegalStateException because detector cannot be created without markers.
 */
class WordSpecificSearchSpamDetector(
    private val scope: CoroutineScope = GlobalScope,
    private val spamMarkers: List<String> = listOf("spam", "advertisement")
) : SpamDetector {

    /**
     * Performs spam detection. For testing purpose add synthetic delay to processing.
     * Delay should be equal to size of text to process multiplied by 10 (each symbols takes 10 millisecond to process).
     * Detection is case-insensitive, so having marker 'spam' both texts 'Spam' and 'spam' are treated as spam.
     * @param text to process
     * @return deferred true if text contains any spam marker.
     */
    override suspend fun detectAsync(text: String): Deferred<Boolean> = TODO()

    private companion object {
        const val DELAY_PER_SYMBOL = 10L
    }
}