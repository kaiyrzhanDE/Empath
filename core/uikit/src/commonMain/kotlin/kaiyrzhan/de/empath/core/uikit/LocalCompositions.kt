package kaiyrzhan.de.empath.core.uikit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

public val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }
