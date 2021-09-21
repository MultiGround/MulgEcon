

package com.multiground.econ

import de.leonhard.storage.Json
import de.leonhard.storage.Yaml
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin


class EconMain : JavaPlugin() {

    private val econConfig = Json("shops.json", this.dataFolder.path)
    private val message = Yaml("message.yml", this.dataFolder.path)

    private var econ: Economy? = null
    companion object {
        lateinit var instance: EconMain
            private set
    }

    override fun onEnable() {
        instance = this
        this.dataFolder.mkdir()
        if(message.getString("plugin.prefix") == null) econConfig.set("plugin.prefix", "[상점]")
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
                            if(doNo) sender.sendMessage(messageParser(message.getString("shop.noShopFound")))
                        }
                    }
                }
                then("list"){
                    executes {
                        val shopList = econConfig.getList("shopName")
                        sender.sendMessage(message.getString("shop.shopListStart"))
                        for(i in shopList.indices){
                            sender.sendMessage(messageParser(message.getString("shop.shopListMessage"), i))
                        }
                        sender.sendMessage(message.getString("shop.shopListFinish"))
                    }
                }
                then("info"){
                    then("shopName" to string()){
                        executes {
                            val shopName: String by it
                            val shopList = econConfig.getList("shopName")
                            if(shopList.indexOf(shopName) != -1){
                                val shopIndex = shopList.indexOf(shopName)
                                sender.sendMessage(messageParser(message.getString("shop.shopInfoStart"), shopIndex))
                                sender.sendMessage(messageParser(message.getString("shop.info.shopOwner"), shopIndex))
                                sender.sendMessage(messageParser(message.getString("shop.info.totalItemCount"), shopIndex))
                            }
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

    fun getEconConf(): Json { return econConfig }

    fun getMessageConf(): Yaml {return message}
}