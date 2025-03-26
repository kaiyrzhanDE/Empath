package kaiyrzhan.de.empath.core.utils.files

import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings

public val dialogSettings: FileKitDialogSettings by lazy {
    createDialogSettings()
}

internal expect fun createDialogSettings(): FileKitDialogSettings