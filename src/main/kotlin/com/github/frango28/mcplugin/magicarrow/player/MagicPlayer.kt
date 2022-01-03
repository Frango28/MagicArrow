package com.github.frango28.mcplugin.magicarrow.player

import com.github.frango28.mcplugin.magicarrow.Main
import org.bukkit.*
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MagicPlayer(p: Player) : MatchPlayer(p) {
    val arrows = mutableMapOf<Arrow, Float>()
    var arrowData: ArrowData = ArrowData.Available(Main.maxArrow)

    var isHoldingBow = false

    fun tickAsync() {
        val data = arrowData
        if (bukkitPlayer.inventory.itemInMainHand.type == Material.BOW) {
            if (!isHoldingBow) isHoldingBow = true
            when (data) {
                is ArrowData.Available -> {
                    sendActionBar("${ChatColor.AQUA} Magic Bow ${ChatColor.GRAY}[${data.amount}]")
                }
                is ArrowData.Reloading -> {
                    sendActionBar("${ChatColor.GREEN}Reloading ${ChatColor.GRAY}[${data.amount}]")
                }
            }
        } else {
            if (isHoldingBow) {
                //前回の tickAsync() の時に弓を持っていたときの処理
                sendActionBar(" ")
                isHoldingBow = false
            }
        }

        //Reloadの処理
        if(data is ArrowData.Reloading){
            data.reloadTick++
            if (data.reloadTick > 15u) {
                //弾数 +1
                data.amount = Main.maxArrow
                data.reloadTick = 0u
                playSound(sound = Sound.BLOCK_BEACON_DEACTIVATE, pitch = 5.0f)
                //パーティクル
                location.add(0.0, 2.2, 0.0).apply {
                    world!!.spawnParticle(Particle.COMPOSTER, this, 4, 0.2, 0.1, 0.2)
                }
                stopReloading()
            }
        }
    }

    fun onShotArrow(arrow: Arrow, force: Float) {
        arrows[arrow] = force
        arrow.isGlowing = true
        arrowData.amount--
    }

    fun onHitArrow(arrow: Arrow) {
        arrows.remove(arrow)
    }

    fun startReloading() {
        if (arrowData.amount < Main.maxArrow) {
            //Reload状態に
            arrowData = ArrowData.Reloading(arrowData.amount)
            playSound(sound = Sound.BLOCK_BEACON_POWER_SELECT, pitch = 2.5f)
            //空中の矢を削除
            arrows.keys.forEach { it.remove() }
            arrows.clear()
        }
    }

    fun stopReloading() {
        arrowData = ArrowData.Available(arrowData.amount)
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