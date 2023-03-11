package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.*;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();
    private static Connection conn = getConnect();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnect() {
        var path = System.getProperty("url");
        var loginUser = System.getProperty("userName");
        var loginPass = System.getProperty("password");
        return DriverManager.getConnection(path, loginUser, loginPass);
    }

    @SneakyThrows
    public static void clearDB() {
        var conn = getConnect();
        runner.execute(conn, "DELETE FROM order_entity;");
        runner.execute(conn, "DELETE FROM payment_entity;");
    }

    @SneakyThrows
    public static String getTransactionStatusDebitCard() {
        var sqlQuery = "SELECT status FROM payment_entity WHERE id IS NOT NULL;";
        return runner.query(conn, sqlQuery, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getTransactionTypeDebitCard() {
        var sqlQuery = "SELECT payment_id FROM order_entity WHERE id IS NOT NULL;";
        return runner.query(conn, sqlQuery, new ScalarHandler<>());
    }
}
