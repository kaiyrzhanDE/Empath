package kaiyrzhan.de.empath.core.utils.logger

import co.touchlab.kermit.Logger

public actual class Logger actual constructor() : BaseLogger {
    public actual override fun d(tag: String, message: String) {
        return Logger.Companion.d(
            tag = tag,
            null,
            message = { message },
        )
    }

    public actual override fun e(tag: String, message: String) {
        return Logger.Companion.e(
            tag = tag,
            null,
            message = { message },
        )
    }
}
