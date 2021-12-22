package com.github.frango28.mcplugin.magicarrow.listener

import com.github.frango28.mcplugin.magicarrow.player.MagicPlayer.Companion.magicPlayer
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent

class ShotListener : Listener {
    @EventHandler
    fun on(e: EntityShootBowEvent) {
        val player = (e.entity as? Player ?: return).magicPlayer
//        info("SHOT!!")
        player.onShotArrow(e.projectile as? Arrow ?: return, e.force)
    }

    @EventHandler
    fun on(e: ProjectileHitEvent) {
        val arrow = e.entity as? Arrow ?: return
        val player = (arrow.shooter as? Player ?: return).magicPlayer

        player.onHitArrow(arrow)
        arrow.remove()
    }
}