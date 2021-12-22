package com.github.frango28.mcplugin.magicarrow.player

import com.github.frango28.mcplugin.magicarrow.Main
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MagicPlayer(p: Player) : MatchPlayer(p) {
    val arrows = mutableMapOf<Arrow, Float>()
    var arrowAmount: UInt = Main.maxArrow
    var reloading: Boolean = false

    fun tickActionBar() {
        if (bukkitPlayer.inventory.itemInMainHand.type == Material.BOW) {
            if (reloading) {
                sendActionBar("${ChatColor.GRAY}Now Reloading ${ChatColor.GREEN}${ChatColor.BOLD}${arrowAmount}")
            } else {
                sendActionBar("${ChatColor.BOLD}残弾 ${ChatColor.GREEN}${ChatColor.BOLD}${arrowAmount}")
            }
        }
    }

    fun onShotArrow(arrow: Arrow, force: Float) {
        arrows[arrow] = force
    }

    fun onHitArrow(arrow: Arrow) {
        arrows.remove(arrow)
    }

    fun startReload() {

    }

    companion object {
        private val playerMap = ConcurrentHashMap<UUID, MagicPlayer>()
        val onlinePlayers: Collection<MagicPlayer>
            get() = Bukkit.getOnlinePlayers().map { it.magicPlayer }

        val Player.magicPlayer: MagicPlayer
            get() = playerMap.getOrPut(uniqueId) { MagicPlayer(this) }

        fun updateMagicPlayer(player: Player): MagicPlayer = player.magicPlayer.apply {
            bukkitPlayer = player
        }
    }
}