package by.OneKa;

import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import java.util.UUID;

public class Spawner {
    Location location;
    EntityTypes type;
    Entity current;
    long deathtime;
    UUID uid;

    public Spawner(final Location location, final EntityTypes type) {
        this.location = location;
        this.current = null;
        this.deathtime = -1L;
        this.uid = UUID.randomUUID();
        this.type = type;
    }

    public void spawn() {
        this.location.getChunk().load(true);
        EntityTypes.spawnEntity(this.type, this.location.clone().add(0.0, 1.0, 0.0), this);
    }

    public World getWorld() {
        return ((CraftWorld) this.location.getWorld()).getHandle();
    }

    public void register(final Entity superZombie) {
        this.current = superZombie;
    }
}
