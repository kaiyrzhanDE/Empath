package kaiyrzhan.de.empath.core.utils

import android.os.Build

public actual val currentPlatform: Platform = AndroidPlatform

public object AndroidPlatform : Platform {
    override val name: String = "Android"

    override val version: String
        get() = Build.VERSION.RELEASE

    override val type: PlatformType = PlatformType.ANDROID
}
