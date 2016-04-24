package pw.kmp.tracker.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockChecker {

    public static boolean isSupported(Player player) {
        Material type = player.getLocation().getBlock().getType();
        return player.isOnGround() || type.equals(Material.WATER) || type.equals(Material.STATIONARY_WATER) ||
                type.equals(Material.LAVA) || type.equals(Material.STATIONARY_LAVA) ||
                type.equals(Material.LADDER) || type.equals(Material.VINE);
    }

    public static boolean isInLava(Player player) {
        Material type = player.getLocation().getBlock().getType();
        return type.equals(Material.LAVA) || type.equals(Material.STATIONARY_LAVA);
    }

}
