package db;

import model.Location;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Properties;

public class DbConnection {

    private static Connection connection;

    private static final Properties properties = getConnectionData();
    private static final String dbhost = properties.getProperty("db.host");
    private static final String username = properties.getProperty("db.username");
    private static final String password = properties.getProperty("db.password");
    private static final String SQL_SELECT_ALL_favorites = "SELECT * FROM favorites ORDER BY lat, lon";
    private static final String SQL_INSERT_INTO_favorites = "INSERT INTO favorites (name, country, lat, lon) VALUES (?,?,?,?)";
    private static final String SQL_DELETE_FROM_favorites = "DELETE FROM favorites WHERE (lat=? AND lon=?)";

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

    public static LinkedHashSet<Favorite> getFavorites() {
        LinkedHashSet<Favorite> favoriteSet = new LinkedHashSet<>();
        try(Connection connection = createNewDbConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_favorites);
            while(resultSet.next()) {
                Favorite favorite = new Favorite();
                favorite.setName(resultSet.getString("name"));
                favorite.setCountry(resultSet.getString("country"));
                favorite.setLat(Double.parseDouble(resultSet.getString("lat")));
                favorite.setLon(Double.parseDouble(resultSet.getString("lon")));
                favoriteSet.add(favorite);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteSet;
    }

    public static int insertIntoFavorites(String name, String country, Double lat, Double lon){
        try(Connection connection = createNewDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_favorites)){
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

    public static int insertIntoFavorites(Location location){
        String name = location.getName();
        String country = location.getCountry();
        double lat = location.getLat();
        double lon = location.getLon();
        try(Connection connection = createNewDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_favorites)){
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

    public static int insertIntoFavorites(Favorite favorite){
        String name = favorite.getName();
        String country = favorite.getCountry();
        double lat = favorite.getLat();
        double lon = favorite.getLon();
        try(Connection connection = createNewDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_favorites)){
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

    public static int deleteFromFavorites(Favorite favorite) {
        double lat = favorite.getLat();
        double lon = favorite.getLon();
        try(Connection connection = createNewDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FROM_favorites)) {
            preparedStatement.setDouble(1,lat);
            preparedStatement.setDouble(2,lon);
            return preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int deleteFromFavorites(double lat, double lon) {
        try(Connection connection = createNewDbConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FROM_favorites)) {
            preparedStatement.setDouble(1,lat);
            preparedStatement.setDouble(2,lon);
            return preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
