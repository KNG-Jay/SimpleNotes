package simpleNotes;

import java.sql.*;

public class Test {
 public static void connect() {
        // connection string
        var url = "jdbc:sqlite:notes.db";

        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        connect();
    }
}
