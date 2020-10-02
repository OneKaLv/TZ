package by.OneKa.database;

import java.sql.*;

public class MySQL {
    public static Connection conn;
    public static Statement statmt;
    public static String mobs = "mobs";
    private static PreparedStatement preparedStatement = null;
    private static int numkill;

    private String createTable = "CREATE TABLE IF NOT EXISTS mobs (player_name varchar(16),mobname varchar (10), numkill INT PRIMARY KEY)";

    public static Connection getConnection() {
        return conn;
    }

    public MySQL(String url, String dbName, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + url + "/" + dbName + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true",
                    user, pass);
            statmt = conn.createStatement();
            statmt.execute(createTable);
            statmt.close();
            numkill = getNumKill()+1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(String name, String mobname) {
        try {
            PreparedStatement e = conn.prepareStatement(
                    "INSERT INTO " + mobs + " (player_name,mobname,numkill) VALUES (?,?,?);");
            e.setString(1, name);
            e.setString(2, mobname);
            e.setInt(3,numkill++);
            e.executeUpdate();
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNumKill() {
        int num = 0;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + MySQL.mobs + " ORDER BY numkill DESC LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            num = resultSet.getInt("numkill");
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }
}
