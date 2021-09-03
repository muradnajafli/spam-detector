package detector

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import processor.LetterProcessor

/**
 * Class to process text concurrently to detect spam.
 * Use `letterProcessor` to split letter to process it concurrently.
 * Concurrent processor should be 'smart' meaning it should terminate text processing
 * in case one of the detection for the part returned that there is a spam in the part.
 * Use scope object from constructor to start main coroutine that will process text and detect spam on each part.
 * To detect spam use `spamDetector` object from constructor. For each part `spamDetector` must be called only once.
 * In case when `spamDetector` will find some spam, `detectAsync` should throw `SpamDetectedException`
 */
class ConcurrentSpamDetector(
    private val letterProcessor: LetterProcessor,
    private val spamDetector: SpamDetector,
    private val scope: CoroutineScope = GlobalScope
) : SpamDetector {

    /**
     * Perform concurrent spam detection. Text will be split by `letterProcessor` on the parts.
     * Each part is processed by the `spamDetector` object.
     * Entire pipeline should be started in the scope provided by constructor.
     * @param text to process
     * @return deferred true if spam detected otherwise deferred false
     */
    override suspend fun detectAsync(text: String): Deferred<Boolean> = TODO()
}