package kaiyrzhan.de.empath.utils

import com.arkivanov.essenty.statekeeper.SerializableContainer
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCoder
import platform.Foundation.NSString
import platform.Foundation.decodeTopLevelObjectOfClass
import platform.Foundation.encodeObject

@Suppress("unused") // Used in Swift
public fun save(coder: NSCoder, state: SerializableContainer) {
    coder.encodeObject(`object` = json.encodeToString(SerializableContainer.serializer(), state), forKey = "state")
}

@Suppress("unused") // Used in Swift
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
public fun restore(coder: NSCoder): SerializableContainer? =
    (coder.decodeTopLevelObjectOfClass(aClass = NSString, forKey = "state", error = null) as String?)?.let {
        try {
            json.decodeFromString(SerializableContainer.serializer(), it)
        } catch (e: Exception) {
            null
        }
    }