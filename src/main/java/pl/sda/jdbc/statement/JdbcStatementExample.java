package pl.sda.jdbc.statement;

import java.sql.*;

public class JdbcStatementExample {

    public static void main(String[] args) throws SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";
        final int maxSurfaceArea = 1000000;
        final String sqlQuery = "SELECT Name, Population FROM country WHERE SurfaceArea >=" + maxSurfaceArea;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC", "root", "root");
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            Class.forName(driver);

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println(name + " - " + population);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
