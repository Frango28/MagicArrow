package com.github.frango28.mcplugin.magicarrow.listener

import com.github.frango28.mcplugin.magicarrow.player.ArrowData
import com.github.frango28.mcplugin.magicarrow.player.MagicPlayer.Companion.magicPlayer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class ShotListener : Listener {
    @EventHandler
    fun on(e: EntityShootBowEvent) {
        val player = (e.entity as? Player ?: return).magicPlayer
//        info("SHOT!!")
        val data = player.arrowData
        // 弓が使用可能かや弾数を確認する
        if (data is ArrowData.Available && data.amount != 0u) {
            //撃った矢に効果を
            player.onShotArrow(e.projectile as? Arrow ?: return, e.force)
        } else {
            //キャンセル
            e.isCancelled = true
            player.playSound(sound = Sound.BLOCK_NOTE_BLOCK_BASS)
        }
    }

    @EventHandler
    fun on(e: ProjectileHitEvent) {
        val arrow = e.entity as? Arrow ?: return
        val player = (arrow.shooter as? Player ?: return).magicPlayer

        player.onHitArrow(arrow)
        arrow.remove()
    }

    @EventHandler
    fun on(e: PlayerDropItemEvent) {
        val bow = e.itemDrop.itemStack
        val player = e.player.magicPlayer

        if (bow.type == Material.BOW) {
            e.isCancelled = true
            player.startReloading()
        }
    }

    @EventHandler
    fun on(e: PlayerInteractEvent) {
        val bow = e.item ?: return
        val player = e.player.magicPlayer

        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            if (bow.type == Material.BOW) {
                if (player.arrowData is ArrowData.Reloading) {
                    player.stopReloading()
                }
            }
        }
    }
}