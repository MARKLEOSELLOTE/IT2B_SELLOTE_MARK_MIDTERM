package com.scc;

import com.scc.dao.DatabaseConnection;
import com.scc.ui.ScenarioMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
            return;
        }
        new ScenarioMenu().run();
    }
}