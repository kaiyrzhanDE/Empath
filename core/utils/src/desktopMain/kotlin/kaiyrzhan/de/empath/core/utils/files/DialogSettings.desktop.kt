package kaiyrzhan.de.empath.core.utils.files

import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.FileKitMacOSSettings

internal actual fun createDialogSettings(): FileKitDialogSettings {
    return FileKitDialogSettings(
        macOS = FileKitMacOSSettings(
            canCreateDirectories = true
        )
    )
}