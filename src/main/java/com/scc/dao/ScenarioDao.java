package com.scc.dao;

import com.scc.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    private static final String INSERT_SQL =
            "INSERT INTO allocations (student_name, building, room_no, monthly_rent) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL =
            "SELECT id, student_name, building, room_no, monthly_rent FROM allocations ORDER BY id";

    public void insert(ScenarioEntity entity) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, entity.studentName());
            ps.setString(2, entity.building());
            ps.setString(3, entity.roomNo());
            ps.setDouble(4, entity.monthlyRent());
            ps.executeUpdate();
        }
    }

    public List<ScenarioEntity> findAll() throws SQLException {
        List<ScenarioEntity> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("building"),
                        rs.getString("room_no"),
                        rs.getDouble("monthly_rent")));
            }
        }
        return list;
    }
}