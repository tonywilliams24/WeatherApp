package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DbConnection {

    private static Connection connection;

    private static final Properties properties = getConnectionData();
    private static final String dbhost = properties.getProperty("db.host");
    private static final String username = properties.getProperty("db.username");
    private static final String password = properties.getProperty("db.password");
    private static final String SQL_SELECT_favorites = "SELECT * FROM favorites";
    private static final String SQL_INSERT_favorites = "INSERT INTO favorites (name, country, lat, lon) VALUES (?,?,?)";

    public static Properties getConnectionData() {
        Properties properties = new Properties();
        String fileName = "src/main/resources/db.properties";
        try (FileInputStream in = new FileInputStream(fileName)) {
            properties.load(in);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties);
        return properties;
    }

    public static Connection createNewDbConnection() {
        try{
            connection = DriverManager.getConnection(dbhost, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static ArrayList<Favorite> getFavorites() {
        ArrayList<Favorite> favoriteList = new ArrayList<>();
        try(Connection connection = createNewDbConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_favorites);
            while(resultSet.next()) {
                Favorite favorite = new Favorite();
                favorite.setName(resultSet.getString("name"));
                favorite.setCountry(resultSet.getString("country"));
                favorite.setLat(Double.parseDouble(resultSet.getString("lat")));
                favorite.setLon(Double.parseDouble(resultSet.getString("lon")));
                favoriteList.add(favorite);
            }
            return favoriteList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int insertIntoFavorites(String name, String country, Double lat, Double lon){
        try(Connection connection = createNewDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_favorites)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,country);
            preparedStatement.setDouble(3,lat);
            preparedStatement.setDouble(4,lon);
            return preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
