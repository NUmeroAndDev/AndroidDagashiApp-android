package jp.numero.android_dagashi.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.StringAnnotation
import androidx.compose.ui.text.annotatedString

/**
 * ref : https://github.com/android/compose-samples/blob/1feea191b51896dcda028dc10436deb25d10c8df/Jetchat/app/src/main/java/com/example/compose/jetchat/conversation/MessageFormatter.kt
 */
val symbolPattern by lazy {
    Regex("""(https?://[^\s\t\n]+)""")
}

@Composable
fun linkedTextFormatter(
    text: String,
    linkColor: Color
): AnnotatedString {
    val tokens = symbolPattern.findAll(text)

    return annotatedString {
        var cursorPosition = 0

        for (token in tokens) {
            append(text.slice(cursorPosition until token.range.first))

            val annotatedString = AnnotatedString(
                text = token.value,
                spanStyle = SpanStyle(
                    color = linkColor
                )
            )
            append(annotatedString)

            val (item, start, end, tag) = StringAnnotation(
                item = token.value,
                start = token.range.first,
                end = token.range.last,
                tag = "LINK"
            )
            addStringAnnotation(scope = tag, start = start, end = end, annotation = item)

            cursorPosition = token.range.last + 1
        }

        if (!tokens.none()) {
            append(text.slice(cursorPosition..text.lastIndex))
        } else {
            append(text)
        }
    }
}