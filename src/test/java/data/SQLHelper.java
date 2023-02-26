package data;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static String path = System.getProperty("url");
    private static String loginUser = System.getProperty("userName");
    private static String loginPass = System.getProperty("password");

    public static void clearDB() {
        var cleanCreditRequest = "DELETE FROM credit_request_entity;";
        var cleanOrder = "DELETE FROM order_entity;";
        var cleanPayment = "DELETE FROM payment_entity;";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(path, loginUser, loginPass)) {
            runner.update(conn, cleanCreditRequest);
            runner.update(conn, cleanOrder);
            runner.update(conn, cleanPayment);
        } catch (SQLException msg) {
            System.out.println("SQLException message:" + msg.getMessage());
        }
    }

    public static String getTransactionStatusDebitCard() throws InterruptedException {
        var sqlQuery = "SELECT status FROM payment_entity WHERE id IS NOT NULL;";
        Thread.sleep(500);
        try (var conn = DriverManager.getConnection(path, loginUser, loginPass);
             var countStmt = conn.createStatement();
             var rs = countStmt.executeQuery(sqlQuery);) {
            if (rs.next()) {
                var status = rs.getString("status");
                return status;
            }
        } catch (SQLException msg) {
            System.out.println("SQLException message:" + msg.getMessage());
        }
        return null;
    }

    public static String getTransactionTypeDebitCard() throws InterruptedException {
        var sqlQuery = "SELECT payment_id FROM order_entity WHERE id IS NOT NULL;";
        Thread.sleep(500);
        try (var conn = DriverManager.getConnection(path, loginUser, loginPass);
             var countStmt = conn.createStatement();
             var rs = countStmt.executeQuery(sqlQuery);) {
            if (rs.next()) {
                var paymentId = rs.getString("payment_id");
                return paymentId;
            }
        } catch (SQLException msg) {
            System.out.println("SQLException message:" + msg.getMessage());
        }
        return null;
    }
}