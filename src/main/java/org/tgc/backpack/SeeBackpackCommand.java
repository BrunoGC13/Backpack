package org.tgc.backpack;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.tgc.backpack.BackpackManager;
import org.tgc.backpack.BackpackCommand;

public class SeeBackpackCommand implements CommandExecutor {
    private final BackpackManager backpackManager;

    public SeeBackpackCommand(BackpackManager backpackManager) {
        this.backpackManager = backpackManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players!");
            return true;
        }

        Player viewer = (Player) sender;

        if (args.length != 1) {
            viewer.sendMessage("§cUsage: /seebackpack <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            viewer.sendMessage("§cPlayer not found or offline!");
            return true;
        }

        Inventory backpack = backpackManager.getBackpack(target);

        Inventory copy = Bukkit.createInventory(viewer, 54, "§6Backpack of " + target.getName());
        copy.setContents(backpack.getContents());

        viewer.openInventory(copy);
        return true;
    }
}
