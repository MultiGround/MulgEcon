
package com.multiground.econ

import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class EconEventHandler : Listener {
    private fun getInstance(): Plugin {
        return EconMain.instance
    }

    @EventHandler
    fun syncOnJoin(e: PlayerJoinEvent) {
        e.player.sendMessage("Syncing local economy with database!")

    }
}