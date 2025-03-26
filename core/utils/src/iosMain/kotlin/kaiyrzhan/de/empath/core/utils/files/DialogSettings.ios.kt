package kaiyrzhan.de.empath.core.utils.files

import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings

internal actual fun createDialogSettings(): FileKitDialogSettings {
    return FileKitDialogSettings.createDefault()
}