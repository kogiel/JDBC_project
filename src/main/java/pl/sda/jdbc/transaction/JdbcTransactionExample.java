package pl.sda.jdbc.transaction;

import java.sql.*;

public class JdbcTransactionExample {

    public static void main(String[] args) throws SQLException {
        final String driver = "com.mysql.cj.jdbc.Driver";
        final int maxSurfaceArea = 1000000;
        final String sqlQuery = "SELECT Name, Population FROM country WHERE SurfaceArea >= ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?serverTimezone=UTC", "root", "root");

//            Wyłączamy tryb autocommit i rozpoczynamy transakcję
            conn.setAutoCommit(false);

            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, maxSurfaceArea);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println(name + " - " + population);
            }
//            Zatwierdzamy transakcję
            conn.commit();
        } catch (ClassNotFoundException e) {
//            Cofamy transakcję
            conn.rollback();
            e.printStackTrace();
        } catch (SQLException e) {
//            Cofamy transakcję
            conn.rollback();
            e.printStackTrace();
        } catch (Exception e) {
//            Cofamy transakcję
            conn.rollback();
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
