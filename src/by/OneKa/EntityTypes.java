package by.OneKa;

import net.minecraft.server.v1_13_R2.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import java.lang.reflect.InvocationTargetException;

public enum EntityTypes {
    CustomZombie(by.OneKa.entity.CustomZombie.class);

    Class<? extends Entity> custom;

    private EntityTypes(final Class<? extends Entity> custom) {
        this.custom = custom;
    }

    public static void spawnEntity(final EntityTypes entityType, final Location loc, final Spawner spawner) {
        try {
            loc.getChunk().load();
            final Entity entity = (Entity) entityType.getEntityClass().getConstructor(Spawner.class).newInstance(spawner);
            entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex4) {
            ex4.printStackTrace();
        }
    }

    public Class<? extends Entity> getEntityClass() {
        return this.custom;
    }
}
