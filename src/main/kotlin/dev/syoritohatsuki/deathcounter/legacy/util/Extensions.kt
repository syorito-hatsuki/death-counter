package dev.syoritohatsuki.deathcounter.legacy.util

import java.net.SocketAddress
import java.util.regex.Pattern

/*   Network   */

fun SocketAddress.isValidIPAddress(): String {
    Pattern.compile("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
        .matcher(toString()).apply {
            return if (find()) group() else "0.0.0.0"
        }
}