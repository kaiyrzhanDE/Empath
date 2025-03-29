package kaiyrzhan.de.empath.core.ui.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.compose.AsyncImagePainter
import coil3.compose.AsyncImagePainter.State
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.rememberPlatformFileCoilModel
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.PickerResultLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import kaiyrzhan.de.empath.core.ui.image.createAuthorizedImageLoader
import kaiyrzhan.de.empath.core.utils.files.dialogSettings

@Composable
public fun rememberImagesPicker(
    title: String,
    maxItems: Int,
    onResult: (List<PlatformFile>) -> Unit,
): PickerResultLauncher {
    return rememberFilePickerLauncher(
        type = FileKitType.Image,
        mode = FileKitMode.Multiple(maxItems = maxItems),
        title = title,
        onResult = { selectedFiles ->
            selectedFiles?.let { onResult(selectedFiles) }
        },
        dialogSettings = dialogSettings
    )
}

@Composable
public fun rememberImagePicker(
    title: String,
    onResult: (PlatformFile) -> Unit,
): PickerResultLauncher {
    return rememberFilePickerLauncher(
        type = FileKitType.Image,
        title = title,
        onResult = { file ->
            file?.let { selectedFile -> onResult(selectedFile) }
        },
        dialogSettings = dialogSettings,
    )
}

@Composable
public fun rememberFilesPicker(
    title: String,
    type: FileKitType = FileKitType.File(),
    maxItems: Int,
    onResult: (List<PlatformFile>) -> Unit,
): PickerResultLauncher {
    return rememberFilePickerLauncher(
        type = type,
        mode = FileKitMode.Multiple(maxItems = maxItems),
        title = title,
        dialogSettings = dialogSettings,
        onResult = { selectedFile ->
            selectedFile?.let { onResult(selectedFile) }
        },
    )
}

@Composable
public fun rememberFilePicker(
    title: String,
    type: FileKitType = FileKitType.File(),
    onResult: (PlatformFile) -> Unit,
): PickerResultLauncher {
    return rememberFilePickerLauncher(
        type = type,
        mode = FileKitMode.Single,
        title = title,
        dialogSettings = dialogSettings,
        onResult = { selectedFile ->
            selectedFile?.let { onResult(selectedFile) }
        },
    )
}

@Composable
public fun rememberImagePainter(
    modifier: Modifier = Modifier,
    model: Any?,
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = error,
    onState: ((State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
    imageLoader: ImageLoader? = null,
): AsyncImagePainter {
    val imageLoaderInstance = imageLoader ?: run {
        val platformContext = LocalPlatformContext.current
        remember { createAuthorizedImageLoader(platformContext) }
    }

    return rememberAsyncImagePainter(
        model = if (model is PlatformFile) rememberPlatformFileCoilModel(model) else model,
        error = error,
        fallback = fallback,
        placeholder = placeholder,
        onLoading = onState,
        onError = onState,
        onSuccess = onState,
        contentScale = contentScale,
        filterQuality = filterQuality,
        imageLoader = imageLoaderInstance,
    )
}

public fun PlatformFile?.toString(): String {
    return this?.name?.substringBeforeLast(".").orEmpty()
}

public inline fun PlatformFile?.ifEmpty(block: () -> Any?): Any? {
    return this ?: block()
}

