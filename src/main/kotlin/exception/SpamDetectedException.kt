package exception

import kotlin.coroutines.cancellation.CancellationException

/**
 * Subclass for cancellation exception to specify when it was canceled by reason of spam detection in the text
 */
class SpamDetectedException : CancellationException()