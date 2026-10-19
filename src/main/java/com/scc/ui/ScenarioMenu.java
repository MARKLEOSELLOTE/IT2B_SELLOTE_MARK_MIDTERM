package com.scc.ui;

import com.scc.dao.ScenarioDao;
import com.scc.model.ScenarioEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {

    private static final String LINE = "=".repeat(50);
    private final ScenarioDao dao = new ScenarioDao();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addAllocation();
                case 2 -> viewAllocations();
                case 3 -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nERROR: Invalid choice. Please enter 1-3.\n");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println(LINE);
        System.out.println("        STUDENT DORMITORY BED ALLOCATION");
        System.out.println(LINE);
        System.out.println("[1] Add New Allocation");
        System.out.println("[2] View All Allocations");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
    }

    private void addAllocation() {
        System.out.println("\n--- ADD NEW BED ALLOCATION ---");
        String name = readNonEmpty("Enter Student Name: ");
        String building = readNonEmpty("Enter Building Name: ");
        String room = readNonEmpty("Enter Room Number: ");
        double rent = readPositiveDouble("Enter Monthly Rent: ");

        try {
            dao.insert(new ScenarioEntity(0, name, building, room, rent));
            System.out.println("\nSUCCESS: Dorm bed allocated successfully!\n");
        } catch (SQLException e) {
            System.out.println("\nERROR: Could not save record - " + e.getMessage() + "\n");
        }
    }

    private void viewAllocations() {
        try {
            List<ScenarioEntity> records = dao.findAll();
            String border = "+----+--------------------+------------+------------+----------------+";
            String wide = "=".repeat(border.length());

            System.out.println("\n" + wide);
            System.out.println("                         DORMITORY BED ALLOCATIONS");
            System.out.println(wide);
            System.out.println(border);
            System.out.printf("| %-2s | %-18s | %-10s | %-10s | %-14s |%n",
                    "ID", "STUDENT NAME", "BUILDING", "ROOM NO", "MONTHLY RENT");
            System.out.println(border);
            for (ScenarioEntity r : records) {
                System.out.printf("| %-2d | %-18s | %-10s | %-10s | %-14.2f |%n",
                        r.id(), r.studentName(), r.building(), r.roomNo(), r.monthlyRent());
            }
            System.out.println(border);
            System.out.println("Total Records Displayed: " + records.size() + "\n");
        } catch (SQLException e) {
            System.out.println("\nERROR: Could not fetch records - " + e.getMessage() + "\n");
        }
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty.");
        }
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("Rent must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (e.g., 3500.00).");
            }
        }
    }
}