package detector

import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import processor.LetterProcessor

@ExperimentalCoroutinesApi
class ConcurrentSpamDetectorTest {

    private lateinit var letterProcessor: LetterProcessor
    private lateinit var singleSpamDetector: SpamDetector
    private lateinit var concurrentSpamDetector: SpamDetector
    private val testScope = TestCoroutineScope()

    @Before
    fun setup() {
        singleSpamDetector = mockk()
        letterProcessor = mockk()
        concurrentSpamDetector = ConcurrentSpamDetector(letterProcessor, singleSpamDetector, testScope)
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun detect_no_spam_single_coroutine() = testScope.runBlockingTest {
        val text = "Single part text"
        val hasSpam = false
        every { letterProcessor.splitLetter(text) } returns listOf(text)
        coEvery { singleSpamDetector.detectAsync(text) } returns CompletableDeferred(hasSpam)
        val isSpamDetected = concurrentSpamDetector.detectAsync(text).await()
        assertFalse(isSpamDetected)
    }

    @Test
    fun detect_has_spam_single_coroutine() = testScope.runBlockingTest {
        val text = "Single part text"
        val hasSpam = true
        every { letterProcessor.splitLetter(text) } returns listOf(text)
        coEvery { singleSpamDetector.detectAsync(text) } returns CompletableDeferred(hasSpam)
        val isSpamDetected = concurrentSpamDetector.detectAsync(text).await()
        assertTrue(isSpamDetected)
    }

    @Test
    fun detect_no_spam_multiple_coroutines() = testScope.runBlockingTest {
        val text = "Single part text"
        val firstTextPart = "Single part"
        val secondTextPart = "text"
        val hasSpam = false
        every { letterProcessor.splitLetter(text) } returns listOf(firstTextPart, secondTextPart)
        coEvery { singleSpamDetector.detectAsync(firstTextPart) } returns CompletableDeferred(hasSpam)
        coEvery { singleSpamDetector.detectAsync(secondTextPart) } returns CompletableDeferred(hasSpam)
        val isSpamDetected = concurrentSpamDetector.detectAsync(text).await()
        assertFalse(isSpamDetected)
    }

    @Test
    fun detect_has_spam_multiple_coroutines() = testScope.runBlockingTest {
        val text = "Single part text"
        val firstTextPart = "Single part"
        val secondTextPart = "text"
        val hasSpam = true
        every { letterProcessor.splitLetter(text) } returns listOf(firstTextPart, secondTextPart)
        coEvery { singleSpamDetector.detectAsync(firstTextPart) } returns CompletableDeferred(hasSpam)
        coEvery { singleSpamDetector.detectAsync(secondTextPart) } returns CompletableDeferred(hasSpam)
        val isSpamDetected = concurrentSpamDetector.detectAsync(text).await()
        assertTrue(isSpamDetected)
    }

    @Test
    fun detect_no_spam_check_invocation_for_all_Letter_parts() = testScope.runBlockingTest {
        val text = "Single part text"
        val firstTextPart = "Single part"
        val secondTextPart = "text"
        val hasSpam = false
        every { letterProcessor.splitLetter(text) } returns listOf(firstTextPart, secondTextPart)
        coEvery { singleSpamDetector.detectAsync(firstTextPart) } returns CompletableDeferred(hasSpam)
        coEvery { singleSpamDetector.detectAsync(secondTextPart) } returns CompletableDeferred(hasSpam)
        concurrentSpamDetector.detectAsync(text).await()
        coVerify(exactly = 1) {
            letterProcessor.splitLetter(text)
        }
        coVerifyAll {
            letterProcessor.splitLetter(text)
        }
    }

    @Test
    fun detect_check_letter_processing_called_once() = testScope.runBlockingTest {
        val text = "Single part text"
        val firstTextPart = "Single part"
        val secondTextPart = "text"
        val hasSpam = false
        every { letterProcessor.splitLetter(text) } returns listOf(firstTextPart, secondTextPart)
        coEvery { singleSpamDetector.detectAsync(firstTextPart) } returns CompletableDeferred(hasSpam)
        coEvery { singleSpamDetector.detectAsync(secondTextPart) } returns CompletableDeferred(hasSpam)
        concurrentSpamDetector.detectAsync(text).await()
        coVerify(exactly = 1) {
            singleSpamDetector.detectAsync(firstTextPart)
        }
        coVerify(exactly = 1) {
            singleSpamDetector.detectAsync(secondTextPart)
        }
        coVerifyAll {
            singleSpamDetector.detectAsync(firstTextPart)
            singleSpamDetector.detectAsync(secondTextPart)
        }
    }

    @Test
    fun detect_spam_multiple_coroutines_check_cancellation() = testScope.runBlockingTest {
        val text = "Single part text"
        val firstTextPart = "Single part"
        val secondTextPart = "text"
        every { letterProcessor.splitLetter(text) } returns listOf(firstTextPart, secondTextPart)
        coEvery { singleSpamDetector.detectAsync(firstTextPart) } answers {
            CompletableDeferred(true)
        }
        coEvery { singleSpamDetector.detectAsync(secondTextPart) } coAnswers {
            // Emulate long delay to check if the coroutine was cancelled because of firstPart contains spam
            delay(Long.MAX_VALUE)
            CompletableDeferred(false)
        }
        val result = concurrentSpamDetector.detectAsync(text).await()
        assertTrue(result)
    }
}