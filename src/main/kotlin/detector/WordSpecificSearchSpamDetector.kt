package detector

import kotlinx.coroutines.*

/**
 * Word-specific spam detector takes marker, which are words usually found in spam letters.
 * Default value is 'spam' and 'advertisement'.
 * Detection should be performed asynchronously in the scope provided in the constructor.
 * Text is treated as spam if there is at least one marker present.
 * In case markers are empty throw IllegalStateException because detector cannot be created without markers.
 */
class WordSpecificSearchSpamDetector(
    private val scope: CoroutineScope = GlobalScope,
    private val spamMarkers: List<String> = listOf("spam", "advertisement")
) : SpamDetector {

    /**
     * Performs spam detection. For testing purposes add synthetic delay to processing.
     * Delay should be equal to the size of the text to process multiplied by 10 (each symbol takes 10 milliseconds to process).
     * Detection is case-insensitive, so having 'spam' marker both texts, 'Spam' and 'spam', are treated as spam.
     * @param text to process
     * @return deferred true if text contains any spam markers.
     */
    override suspend fun detectAsync(text: String): Deferred<Boolean> = TODO()

    private companion object {
        const val DELAY_PER_SYMBOL = 10L
    }
}