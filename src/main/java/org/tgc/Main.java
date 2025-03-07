package org.tgc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.tgc.backpack.BackpackManager;
import org.tgc.backpack.SeeBackpackCommand;
import org.tgc.backpack.BackpackCommand;

public final class Main extends JavaPlugin implements Listener {
    private org.tgc.backpack.BackpackManager backpackManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Example Plugin was enabled!");

        backpackManager = new BackpackManager(this);

        getCommand("backpack").setExecutor(new BackpackCommand(backpackManager));
        getCommand("seebackpack").setExecutor(new SeeBackpackCommand(backpackManager));

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Example Plugin was disabled!");
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        backpackManager.saveBackpack((Player) event.getPlayer());
    }
}

