package kaiyrzhan.de.empath.core.ui.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

public class ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        val cleaned = original.filter { it.isDigit() }

        val formatted = cleaned.reversed()
            .chunked(3)
            .joinToString(" ")
            .reversed()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = formatted.length
            override fun transformedToOriginal(offset: Int): Int = cleaned.length
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

