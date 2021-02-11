package detector

import kotlinx.coroutines.Deferred

/**
 * Interface that will process text asynchronously and will return result of spam detection
 */
interface SpamDetector {
    /**
     * Method to process text asynchronously to detect spam
     * @param text to process
     * @return deferred true if spam detected otherwise deferred false
     */
    suspend fun detectAsync(text: String): Deferred<Boolean>
}