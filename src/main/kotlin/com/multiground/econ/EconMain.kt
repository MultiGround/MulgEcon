

package com.multiground.econ

import de.leonhard.storage.Json
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KProperty


class EconMain : JavaPlugin() {

    private val econConfig = Json("shops.json", this.dataFolder.path)

    private var econ: Economy? = null
    companion object {
        lateinit var instance: EconMain
            private set
    }

    override fun onEnable() {
        instance = this
        this.dataFolder.mkdir()
        if(econConfig.getString("pluginPrefix") == null) econConfig.set("pluginPrefix", "[상점]")
        val respondPrefix = econConfig.getString("pluginPrefix")
        if (!setupEconomy() ) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", description.name))
            server.pluginManager.disablePlugin(this)
            return
        }

        kommand {
            register("shop") {
                requires { playerOrNull != null }
                then("open"){
                    then("shopName" to string()){
                        executes {
                            val shopName: String by it
                            val shopList = econConfig.getList("shopName")
                            var doNo = true
                            for(i in shopList.indices){
                                if(shopName.contentEquals(shopList[i].toString())){
                                    OpenShop().open(shopName)
                                    doNo = false
                                }
                            }
                            if(doNo) sender.sendMessage("$respondPrefix 상점을 찾을 수 없습니다!")
                        }
                    }
                }
            }
        }

        logger.info("Economy Shop Initialized!")
        server.pluginManager.registerEvents(EconEventHandler(), this)
    }

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return false
        econ = rsp.provider
        return true
    }

    fun getEconomy(): Economy? {return econ}

    fun getConf(): Json { return econConfig }
}