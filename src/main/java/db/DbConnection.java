package db;

import model.Location;

import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    private static String dbhost = "jdbc:mysql://localhost:3306/locationsDb";
    private static String username = "root";
    private static String password = "notStrongRootPass";
    private static Connection connection;
    private static String sql_select_favorites = "Select * From favorites";
    private static Statement statement;
    private static ResultSet resultSet;

    public static Connection createNewDbConnection() {
        try{
            connection = DriverManager.getConnection(dbhost, username, password);
            return connection;
        }
        catch (SQLException e) {
            System.out.println("Connection Error");
            return null;
        }
    }
    public static ArrayList<Favorite> getFavorites() {
        ArrayList<Favorite> favoriteList = new ArrayList<>();
        try(Connection connection = createNewDbConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_select_favorites);
            while(resultSet.next()) {
                Favorite favorite = new Favorite();
                favorite.setCityName(resultSet.getString("cityName"));
                favorite.setStateCode(resultSet.getString("stateCode"));
                favorite.setCountryCode(resultSet.getString("countryCode"));
                favorite.setCityId(resultSet.getString("cityId"));
                favorite.setLat(Double.parseDouble(resultSet.getString("lat")));
                favorite.setLon(Double.parseDouble(resultSet.getString("lon")));
                favorite.setZipCode(resultSet.getString("zipCode"));
                favoriteList.add(favorite);
            }
            return favoriteList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
