
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
    fun onPlayerJoin(e: PlayerJoinEvent) {
        getInstance().logger.info("Hello World!")
        e.player.sendMessage(Component.text().content("Hello World!").build())
    }
}