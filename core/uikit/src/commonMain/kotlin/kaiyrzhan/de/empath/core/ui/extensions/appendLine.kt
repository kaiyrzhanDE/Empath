package kaiyrzhan.de.empath.core.ui.extensions

public fun Appendable.appendSpace(): Appendable = append(' ')
public fun Appendable.appendColon(): Appendable = append(':')
public fun Appendable.appendDot(): Appendable = append('•')
public fun Appendable.appendSlash(): Appendable = append('/')

public fun StringBuilder.appendSpace(): Appendable = append(' ')
public fun StringBuilder.append(): Appendable = append(' ')
public fun StringBuilder.appendDot(): Appendable = append('•')
public fun StringBuilder.appendSlash(): Appendable = append('/')
