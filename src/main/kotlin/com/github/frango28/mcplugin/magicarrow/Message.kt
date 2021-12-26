package com.github.frango28.mcplugin.magicarrow

import com.github.frango28.mcplugin.magicarrow.Main.Companion.plugin
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun info(msg: String) {
    plugin.logger.info(msg)
}

fun sendException(sender: CommandSender, e: Exception) {
    sender.sendMessage(exception(e))
    e.printStackTrace()
}

fun customText(prefix: String, vararg msg: String) =
    "${ChatColor.GRAY}[$prefix] ${ChatColor.WHITE}${msg.reduce { acc, s -> acc.plus("${ChatColor.WHITE}$s") }}"

fun text(vararg msg: String) = customText(Main.pluginPrefix, *msg)

fun customException(prefix: String, vararg msg: String): String =
    "${ChatColor.GRAY}[$prefix] ${ChatColor.RED}${msg.reduce { acc, s -> acc.plus("${ChatColor.RED}$s") }}"

fun exception(vararg msg: String): String = customException(Main.pluginPrefix, *msg)

fun exception(e: Exception): String = "${ChatColor.GRAY}[${e::class.java.simpleName}] ${ChatColor.RED}${e.message}"
