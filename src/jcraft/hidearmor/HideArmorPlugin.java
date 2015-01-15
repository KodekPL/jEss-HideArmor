package jcraft.hidearmor;

import jcraft.pl.PacketListenerPlugin;
import jcraft.pl.PacketType;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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

    public static boolean isVisibleArmorItem(ItemStack item) {
        switch (item.getType()) {
        case SKULL_ITEM:
        case BANNER:
            return true;
        case LEATHER_HELMET:
        case LEATHER_CHESTPLATE:
        case LEATHER_LEGGINGS:
        case LEATHER_BOOTS:
            if (item.hasItemMeta()) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

                if (meta.getColor() != null && meta.getColor() != Bukkit.getItemFactory().getDefaultLeatherColor()) {
                    return true;
                }
            }
            return false;
        default:
            return false;
        }
    }

}
