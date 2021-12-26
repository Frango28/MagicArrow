package com.github.frango28.mcplugin.magicarrow.player

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import java.util.*

open class MatchPlayer(p: Player) : Comparable<MatchPlayer> {
    //    PlayerData 関係
    val name: String
        get() = bukkitPlayer.name
    val uuid: UUID
        get() = bukkitPlayer.uniqueId
    val isOnline: Boolean
        get() = bukkitPlayer.isOnline
    var bukkitPlayer: Player = p
    val location: Location
        get() = bukkitPlayer.location
    var gameMode: GameMode
        get() = bukkitPlayer.gameMode
        set(value) {
            bukkitPlayer.gameMode = value
        }

    //    Message 関係
    fun sendMessage(msg: String) = bukkitPlayer.sendMessage(msg)
    fun sendActionBar(msg: String) = bukkitPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(msg))
    fun sendTitle(title: String = " ", sub: String = " ", fadeIn: Int = 10, stay: Int = 50, fadeOut: Int = 10) =
        bukkitPlayer.sendTitle(title, sub, fadeIn, stay, fadeOut)

    fun playSound(
        loc: Location = location,
        sound: Sound,
        category: SoundCategory = SoundCategory.MASTER,
        volume: Float = 1f,
        pitch: Float = 1f,
    ) =
        bukkitPlayer.playSound(loc, sound, category, volume, pitch)

    //    動作関係
    fun teleport(loc: Location) = bukkitPlayer.teleport(loc)

    override fun equals(other: Any?): Boolean {
        val player = (other as? MatchPlayer) ?: return false
        return player.uuid == uuid
    }

    override fun hashCode(): Int = uuid.hashCode()
    override fun toString(): String = "$name(uuid = $uuid)"
    override fun compareTo(other: MatchPlayer): Int = name.compareTo(other.name)
}
