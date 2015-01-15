package jcraft.hidearmor;

import jcraft.pl.PacketListenerPlugin;
import jcraft.pl.PacketType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HideArmorPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final PacketListenerPlugin packetListenerPlugin = (PacketListenerPlugin) this.getServer().getPluginManager().getPlugin("jPacketListener");

        packetListenerPlugin.registerPacketType(PacketType.OUT_ENTITY_EQUIPMENT);

        this.getServer().getPluginManager().registerEvents(new PacketListener(), this);
    }

    public static boolean isEntityIdPlayer(World world, int entityId) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(world) && player.getEntityId() == entityId) {
                return true;
            }
        }

        return false;
    }

    public static boolean isSkippedArmorItem(Material material) {
        switch (material) {
        case SKULL_ITEM:
        case BANNER:
            return true;
        default:
            return false;
        }
    }

}
