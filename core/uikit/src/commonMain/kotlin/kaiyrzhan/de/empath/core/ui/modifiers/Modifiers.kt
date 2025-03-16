package kaiyrzhan.de.empath.core.ui.modifiers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

public fun Modifier.defaultMaxWidth(): Modifier {
    return this.then(
        Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
    )
}