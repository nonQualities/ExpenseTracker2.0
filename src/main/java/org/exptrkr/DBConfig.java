package org.exptrkr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class DBConfig {
        public static final String URL = "jdbc:sqlite:expensetracker.db";

        public static Connection getConnection() throws SQLException, SQLException {
            return DriverManager.getConnection(URL);}

    }
