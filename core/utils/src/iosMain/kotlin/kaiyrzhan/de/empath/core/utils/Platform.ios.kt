package kaiyrzhan.de.empath.core.utils

import platform.UIKit.UIDevice

public actual val currentPlatform: Platform = IOSPlatform

public object IOSPlatform : Platform {
    override val name: String
        get() = UIDevice.currentDevice.systemName

    override val version: String
        get() = UIDevice.currentDevice.systemVersion

    override val type: PlatformType = PlatformType.IOS
}
