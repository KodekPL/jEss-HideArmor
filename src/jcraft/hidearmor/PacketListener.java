package jcraft.hidearmor;

import jcraft.pl.PacketType;
import jcraft.pl.event.PacketPlayOutEvent;
import net.minecraft.server.v1_8_R1.ItemStack;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PacketListener implements Listener {

    @EventHandler
    public void onPacketPlayOut(PacketPlayOutEvent event) {
        if (event.getPacketType() != PacketType.OUT_ENTITY_EQUIPMENT) {
            return;
        }

        final Integer slot = getItemSlot(event);

        if (slot == null || slot == 0) { // Is null or item in hand
            return;
        }

        final Object item = getItem(event);

        if (item == null) {
            return;
        }

        final Material material = CraftItemStack.asBukkitCopy((ItemStack) item).getType();

        if (HideArmorPlugin.isSkippedArmorItem(material)) {
            return;
        }

        final Integer entityId = getEntityId(event);

        if (entityId == null) {
            return;
        }

        if (HideArmorPlugin.isEntityIdPlayer(event.getPlayer().getWorld(), entityId)) {
            removeItem(event);
        }
    }

    public Integer getItemSlot(PacketPlayOutEvent event) {
        try {
            return (Integer) event.getPacketValue("b");
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getEntityId(PacketPlayOutEvent event) {
        try {
            return (Integer) event.getPacketValue("a");
        } catch (Exception e) {
            return null;
        }
    }

    public Object getItem(PacketPlayOutEvent event) {
        try {
            return event.getPacketValue("c");
        } catch (Exception e) {
            return null;
        }
    }

    public void removeItem(PacketPlayOutEvent event) {
        try {
            event.setPacketValue("c", null);
        } catch (Exception e) {

        }
    }

}
