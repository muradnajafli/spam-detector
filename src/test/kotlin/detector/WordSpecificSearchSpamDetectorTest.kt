package detector

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WordSpecificSearchSpamDetectorTest {

    private lateinit var spamDetector: SpamDetector
    private val testScope = TestCoroutineScope()

    @Before
    fun setup() {
        spamDetector = WordSpecificSearchSpamDetector(scope = testScope)
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun detect_empty_content() = testScope.runBlockingTest {
        val containsSpam = spamDetector.detectAsync("").await()
        assertFalse(containsSpam)
    }

    @Test
    fun detect_no_spam_in_content() = testScope.runBlockingTest {
        val containsSpam = spamDetector.detectAsync("Some valid content").await()
        assertFalse(containsSpam)
    }

    @Test
    fun detect_spam_in_content() = testScope.runBlockingTest {
        val containsSpam = spamDetector.detectAsync("Hey it is spam").await()
        assertTrue(containsSpam)
    }

    @Test
    fun detect_advertisement_in_content() = testScope.runBlockingTest {
        val containsSpam = spamDetector.detectAsync("Checkout new advertisement").await()
        assertTrue(containsSpam)
    }

    @Test(expected = IllegalStateException::class)
    fun create_detector_empty_spam_markers() {
        WordSpecificSearchSpamDetector(spamMarkers = emptyList())
    }

    @Test
    fun detect_use_custom_markers() {
        val markers = listOf("marker1", "marker2", "spam")
        val detector = WordSpecificSearchSpamDetector(scope = testScope, spamMarkers = markers)
        testScope.runBlockingTest {
            val hasSpam = detector.detectAsync("spam").await()
            assertTrue(hasSpam)
        }
    }

    @Test
    fun detect_check_ignore_case() {
        val markers = listOf("marker1")
        val detector = WordSpecificSearchSpamDetector(scope = testScope, spamMarkers = markers)
        testScope.runBlockingTest {
            val hasSpam = detector.detectAsync("MarKer1").await()
            assertTrue(hasSpam)
        }
    }
}