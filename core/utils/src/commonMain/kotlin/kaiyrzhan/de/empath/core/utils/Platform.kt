package kaiyrzhan.de.empath.core.utils

public interface Platform {
    public val name: String
    public val version: String
    public val type: PlatformType

    public companion object {
        public val current: Platform
            get() = currentPlatform
    }
}

public enum class PlatformType {
    IOS,
    ANDROID,
    DESKTOP;

    public fun isAndroid(): Boolean{
        return this == ANDROID
    }
    public fun isDesktop(): Boolean{
        return this == DESKTOP
    }
    public fun ioIOS(): Boolean{
        return this == IOS
    }
}

public expect val currentPlatform: Platform
