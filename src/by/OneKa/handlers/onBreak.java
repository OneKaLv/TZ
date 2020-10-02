package by.OneKa.handlers;

import by.OneKa.EntityTypes;
import by.OneKa.Main;
import by.OneKa.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class onBreak implements Listener {

    public onBreak(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.DIRT){
            Spawner spawner = new Spawner(event.getBlock().getLocation(),EntityTypes.CustomZombie);
            spawner.spawn();
        }
    }
}
