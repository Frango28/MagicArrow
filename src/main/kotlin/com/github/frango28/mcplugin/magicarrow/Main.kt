package com.github.frango28.mcplugin.magicarrow

import com.github.frango28.mcplugin.magicarrow.listener.JoinQuitListener
import com.github.frango28.mcplugin.magicarrow.listener.ShotListener
import com.github.frango28.mcplugin.magicarrow.player.MagicPlayer
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class Main : JavaPlugin() {
    companion object {
        internal lateinit var plugin: JavaPlugin
        internal lateinit var console: ConsoleCommandSender
        const val pluginPrefix: String = "MagicArrow"

        const val maxArrow: UInt = 5u
    }

    override fun onEnable() {
        plugin = this
        console = server.consoleSender

        registerAllListeners()

        object : BukkitRunnable() {
            override fun run() {
                MagicPlayer.onlinePlayers.forEach player@{ p ->
                    p.arrows.forEach arrow@{ (arrow, force) ->
                        if (arrow.isDead) {
                            p.arrows.remove(arrow)
                            return@arrow
                        }
                        arrow.velocity = p.location.direction.multiply(force)
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1)

        object : BukkitRunnable() {
            override fun run() {
                MagicPlayer.onlinePlayers.forEach {
                    it.tickActionBar()
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 1)
    }

    override fun onDisable() {

    }

    private fun registerAllListeners() {
        registerLister(JoinQuitListener())
        registerLister(ShotListener())
    }

    private fun registerLister(listener: Listener) {
        plugin.server.pluginManager.registerEvents(listener, plugin)
    }
}
