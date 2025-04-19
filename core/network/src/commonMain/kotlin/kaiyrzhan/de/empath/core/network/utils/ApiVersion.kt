package kaiyrzhan.de.empath.core.network.utils

public enum class ApiVersion(private val version: String) {
    V1("v1"),
    V2("v2");

    override fun toString(): String {
        return version
    }
}