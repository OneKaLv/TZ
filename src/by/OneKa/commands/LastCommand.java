package by.OneKa.commands;

import by.OneKa.database.MySQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LastCommand extends AbstractCommand {
    public LastCommand() {
        super("last");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Connection conn = MySQL.getConnection();
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + MySQL.mobs + " ORDER BY numkill DESC LIMIT 10");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    sender.sendMessage(resultSet.getString("mobname"));
                }
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
