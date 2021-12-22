package com.github.frango28.mcplugin.magicarrow.listener

import com.github.frango28.mcplugin.magicarrow.player.MagicPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinQuitListener : Listener {
    @EventHandler
    fun on(e: PlayerJoinEvent) {
        MagicPlayer.updateMagicPlayer(e.player)

    }

//    @EventHandler
//    fun on (e:PlayerQuitEvent){
//
//    }
}