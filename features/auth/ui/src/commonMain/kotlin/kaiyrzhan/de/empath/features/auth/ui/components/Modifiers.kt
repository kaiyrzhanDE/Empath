package kaiyrzhan.de.empath.features.auth.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun Modifier.defaultMaxWidth(): Modifier {
    return this.then(
        Modifier
            .widthIn(max = 350.dp)
            .fillMaxWidth()
    )
}