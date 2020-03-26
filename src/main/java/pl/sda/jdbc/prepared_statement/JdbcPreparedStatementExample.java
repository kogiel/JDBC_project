package pl.sda.jdbc.prepared_statement;

import java.sql.*;

public class JdbcPreparedStatementExample {

    public static void main(String[] args) throws SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";
        final int maxSurfaceArea = 1000000;
        final String sqlQuery = "SELECT Name, Population FROM country WHERE SurfaceArea >= ?";

        ResultSet resultSet = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC", "root", "root");
             PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
        ) {
            Class.forName(driver);

            preparedStatement.setInt(1, maxSurfaceArea);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println(name + " - " + population);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
