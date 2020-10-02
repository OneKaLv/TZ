package by.OneKa.entity;

import by.OneKa.Spawner;
import by.OneKa.database.MySQL;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Random;

public class CustomZombie extends EntityMonster implements Mob {

    private final LivingEntity livingEntity;
    private Spawner spawner;

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
        this.livingEntity = (LivingEntity) this.getBukkitEntity();
        this.setCustomName(new ChatComponentText(this.getMobName()));
        this.setCustomNameVisible(true);
        (this.spawner = spawner).register(this);
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
            MySQL.insert(killer.getName(),this.getMobName());
            org.bukkit.inventory.ItemStack magic_dust = new ItemStack(Material.ROTTEN_FLESH);
            ItemMeta magiMeta = magic_dust.getItemMeta();
            magiMeta.setDisplayName(killer.getName());
            magic_dust.setItemMeta(magiMeta);
            Bukkit.getWorlds().get(0).dropItemNaturally(this.getBukkitEntity().getLocation(), magic_dust);
        }
        super.die();
    }
}
