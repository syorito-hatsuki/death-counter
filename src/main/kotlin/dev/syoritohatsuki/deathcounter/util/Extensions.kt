package dev.syoritohatsuki.deathcounter.util

import java.net.SocketAddress
import java.util.regex.Matcher
import java.util.regex.Pattern

/*   Network   */

fun SocketAddress.isValidIPAddress(): String {
    val matcher: Matcher =
        Pattern.compile("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
            .matcher(toString())

    return if (matcher.find()) matcher.group() else "0.0.0.0"
}