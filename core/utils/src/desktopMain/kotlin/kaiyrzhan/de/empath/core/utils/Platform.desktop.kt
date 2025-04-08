package kaiyrzhan.de.empath.core.utils

public actual val currentPlatform: Platform = DesktopPlatform

public object DesktopPlatform: Platform {
    override val name: String
        get() = System.getProperty("os.name") ?: ""

    override val version: String
        get() = System.getProperty("os.version") ?: ""

    override val type: PlatformType = PlatformType.DESKTOP
}
