package com.multiground.econ;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

class Shop<PaginatedGui>
{
    List<shopItem> shopItem;
    String guiTitle;
    Player player;

    Shop(List<shopItem> shopItem, String guiTitle, Player player)
    {
        this.shopItem = shopItem;
        this.guiTitle= guiTitle;
        this.player = player;
    }

    void create(){
        dev.triumphteam.gui.guis.PaginatedGui gui = Gui.paginated().title(Component.text(guiTitle)).rows(6).create();

        gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> gui.previous()));
        gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> gui.next()));

        for(int i = 0; i < shopItem.toArray().length ;i++)
        {
            GuiItem guiItem = ItemBuilder.from(shopItem.get(i).material).name(Component.text(shopItem.get(i).name)).asGuiItem();
            gui.addItem(guiItem);
        }

        openGui(player, gui);

    }
    void openGui(Player player, dev.triumphteam.gui.guis.PaginatedGui gui)
    {
        gui.open(player);
    }
}

class shopItem
{
    String name;
    Material material;
    int value;

    shopItem(String name, Material material, int value)
    {
        this.name = name;
        this.material = material;
        this. value = value;
    }
}

//TODO : add transaction
//TODO : item price and coloring
