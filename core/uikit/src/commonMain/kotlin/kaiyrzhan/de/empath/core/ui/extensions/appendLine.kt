package kaiyrzhan.de.empath.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme

public fun Appendable.appendSpace(): Appendable = append(' ')
public fun Appendable.appendColon(): Appendable = append(':')
public fun Appendable.appendDot(): Appendable = append('•')
public fun Appendable.appendSlash(): Appendable = append('/')

@Composable
public fun AnnotatedString.Builder.appendRequiredMarker(): AnnotatedString.Builder {
    withStyle(SpanStyle(color = EmpathTheme.colors.error)) {
        append('*')
    }
    return this
}


public fun StringBuilder.appendSpace(): Appendable = append(' ')
public fun StringBuilder.append(): Appendable = append(' ')
public fun StringBuilder.appendDot(): Appendable = append('•')
public fun StringBuilder.appendSlash(): Appendable = append('/')
