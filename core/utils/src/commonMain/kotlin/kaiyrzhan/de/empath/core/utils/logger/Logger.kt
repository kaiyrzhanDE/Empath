package kaiyrzhan.de.empath.core.utils.logger

public expect class Logger() : BaseLogger {
    override fun d(tag: String, message: String): Unit
    override fun e(tag: String, message: String)
}

public interface BaseLogger {
    public fun d(tag: String, message: String): Unit
    public fun e(tag: String, message: String): Unit
}
