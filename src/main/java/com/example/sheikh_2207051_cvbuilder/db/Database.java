package com.example.sheikh_2207051_cvbuilder.db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_NAME = "cvbuilder.db";
    private static final String URL;

    static {
        Path p = Paths.get(System.getProperty("user.dir")).resolve(DB_NAME);
        URL = "jdbc:sqlite:" + p.toAbsolutePath().toString();
        try {
            if (!Files.exists(p)) Files.createFile(p);
        } catch (Exception e) {
            // ignore; SQLite will create file if needed
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        ensureTables(conn);
        return conn;
    }

    private static void ensureTables(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS cv (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fullName TEXT, email TEXT, phone TEXT, address TEXT, " +
                "education TEXT, skills TEXT, experience TEXT, projects TEXT, photo BLOB)";
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
