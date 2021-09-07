

package com.multiground.econ

import org.bukkit.plugin.java.JavaPlugin

class EconMain : JavaPlugin() {

    companion object {
        lateinit var instance: EconMain
            private set
    }

    override fun onEnable() {
        instance = this
        logger.info("Hello World!")
        server.pluginManager.registerEvents(EconEventHandler(), this)
    }
}