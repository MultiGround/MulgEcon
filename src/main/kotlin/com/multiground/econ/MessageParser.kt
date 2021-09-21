package com.multiground.econ

import org.bukkit.plugin.Plugin

fun messageParser(message: String, shopNum: Int? = null): String{
    val prefix = getInstance().getMessageConf().getString("plugin.prefix")
    message.replace("(\\\$prefix)".toRegex(), prefix)
    if(shopNum != null){
        val shopName = getInstance().getEconConf().getList("shopName")[shopNum].toString()
        message.replace("(\\\$shopName)".toRegex(), shopName)
        message.replace("(\\\$shopOwner)".toRegex(), getInstance().getEconConf().getString("shop.${shopName}.owner").toString())
        message.replace("(\\\$shopItemCount)".toRegex(), getInstance().getEconConf().getString("shop.${shopName}.totalItem").toString())
    }
    return message
}

private fun getInstance(): EconMain {
    return EconMain.instance
}