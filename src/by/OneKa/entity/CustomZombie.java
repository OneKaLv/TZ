package by.OneKa.entity;

import by.OneKa.Spawner;
import by.OneKa.database.MySQL;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Random;

public class CustomZombie extends EntityMonster implements Mob {

    public CustomZombie(Spawner spawner) {
        super(EntityTypes.ZOMBIE, spawner.getWorld());
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(this.goalSelector, Sets.newLinkedHashSet());
            bField.set(this.targetSelector, Sets.newLinkedHashSet());
            cField.set(this.goalSelector, Sets.newLinkedHashSet());
            cField.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        this.goalSelector.a(0, new PathfinderGoalAvoidTarget<>(this, EntityPlayer.class, 10, 0.3, 0.6));
        this.setCustomName(new ChatComponentText(this.getMobName()));
        this.setCustomNameVisible(true);
    }

    @Override
    public String getMobName() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char letter = (char) (0x61 + random.nextInt(26));
            sb.insert(i, letter);
        }
        return sb.toString();
    }

    @Override
    public void die() {
        if (this.killer instanceof EntityPlayer) {
            MySQL.insert(killer.getName(), this.getMobName());
            Location loc = this.getBukkitEntity().getLocation();
            Item entityItem = loc.getWorld().dropItem(loc, new ItemStack(Material.ROTTEN_FLESH));
            entityItem.setCustomNameVisible(true);
            DataWatcher data;
            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                System.out.println(p.getName());
                entityItem.setCustomName(p.getName());
                data = ((CraftEntity)entityItem).getHandle().getDataWatcher();
                PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityItem.getEntityId(),data,true);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutEntityMetadata);
            }
        }
        super.die();
    }
}
