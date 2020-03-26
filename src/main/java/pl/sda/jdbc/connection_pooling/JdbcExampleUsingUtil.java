package pl.sda.jdbc.connection_pooling;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcExampleUsingUtil {

    public static void main(String[] args) {
        JdbcExampleUsingUtil util = new JdbcExampleUsingUtil();
        List<City> cities = util.getCities();

        cities.forEach(c -> System.out.println(c.getName() + " - " + c.getPopulation()));
    }

    private List<City> getCities() {
        List<City> cities = new ArrayList<>();
        final String sqlQuery = "SELECT Name, Population FROM city";
        try (
                Connection conn = DataBaseUtil.getInstance().getConnection();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery);
        ) {
            while (resultSet.next()) {
                String cityName = resultSet.getString("Name");
                int cityPopulation = resultSet.getInt("Population");
                City city = new City(cityName, cityPopulation);
                cities.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }
}
