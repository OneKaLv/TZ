package by.OneKa;

import by.OneKa.commands.LastCommand;
import by.OneKa.database.MySQL;
import by.OneKa.handlers.onBreak;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        new MySQL("localhost","antiscam","root","8pdSReCgpwqD");
        new onBreak(this);
        new LastCommand().register();
    }

    @Override
    public void onDisable() {

    }
}