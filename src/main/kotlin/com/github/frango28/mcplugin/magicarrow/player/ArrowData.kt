package com.github.frango28.mcplugin.magicarrow.player

sealed class ArrowData(var amount: UInt) {
    class Available(amount: UInt) : ArrowData(amount)

    class Reloading(amount: UInt) : ArrowData(amount) {
        var reloadTick: UInt = 0u
    }
}