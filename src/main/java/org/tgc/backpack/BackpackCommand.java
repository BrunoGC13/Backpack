package org.tgc.backpack;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.tgc.backpack.BackpackManager;
import org.bukkit.inventory.ItemStack;

public class BackpackCommand implements CommandExecutor, Listener {
    private final BackpackManager backpackManager;

    public BackpackCommand(BackpackManager backpackManager) {
        this.backpackManager = backpackManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory backpack = backpackManager.getBackpack(player);
            player.openInventory(backpack);
            return true;
        } else {
            sender.sendMessage("This command can only be used by players!");
            return false;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getInventory().getHolder() instanceof Player) {
            Player player = (Player) event.getWhoClicked();


            if (event.getSlot() != -999) {

                backpackManager.saveBackpack(player);
            }
        }
    }
}
