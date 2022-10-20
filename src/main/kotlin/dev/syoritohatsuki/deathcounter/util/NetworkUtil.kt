package dev.syoritohatsuki.deathcounter.util

import java.net.SocketAddress
import java.util.regex.Matcher
import java.util.regex.Pattern

object NetworkUtil {
    fun isValidIPAddress(ip: SocketAddress): String {
        val m: Matcher =
            Pattern.compile("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
                .matcher(ip.toString())

        return if (m.find()) m.group() else "0.0.0.0"
    }
}