package org.tgc.backpack;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BackpackManager {
    private final org.tgc.Main plugin;
    private final Map<UUID, Inventory> backpacks;
    private final File dataFile;
    private final FileConfiguration dataConfig;

    public BackpackManager(org.tgc.Main plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "backpacks.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        this.backpacks = new java.util.HashMap<>();
    }

    public Inventory getBackpack(Player player) {
        UUID uuid = player.getUniqueId();
        if (backpacks.containsKey(uuid)) {
            return backpacks.get(uuid);
        }

        Inventory backpack = Bukkit.createInventory(player, 54, "§6Backpack");

        if (dataConfig.contains(uuid.toString())) {

            List<?> itemList = dataConfig.getList(uuid.toString());
            if (itemList != null) {
                List<ItemStack> items = new ArrayList<>();

                for (Object item : itemList) {
                    if (item instanceof ItemStack) {
                        items.add((ItemStack) item);
                    }
                }
                backpack.setContents(items.toArray(new ItemStack[0]));
            }
        }

        backpacks.put(uuid, backpack);
        return backpack;
    }

    public void saveBackpack(Player player) {
        UUID uuid = player.getUniqueId();
        if (!backpacks.containsKey(uuid)) return;

        Inventory backpack = backpacks.get(uuid);

        dataConfig.set(uuid.toString(), backpack.getContents());

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save backpack for " + player.getName());
        }
    }

    public void handleItemTaken(Player player) {
        saveBackpack(player);
    }

    public void handleItemPut(Player player) {
        saveBackpack(player);
    }
}
