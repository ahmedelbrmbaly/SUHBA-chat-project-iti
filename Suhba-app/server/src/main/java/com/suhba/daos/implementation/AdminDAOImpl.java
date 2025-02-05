package com.suhba.daos.implementation;




import com.suhba.daos.DatabaseConnection;
import com.suhba.daos.interfaces.AdminDao;
import com.suhba.database.entities.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDAOImpl implements AdminDao {


    private static final String INSERT_SQL = "INSERT INTO Admins (adminName, adminEmail, password, isActive) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT adminId, adminName, adminEmail, password, isActive FROM Admins WHERE adminId = ?";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT adminId, adminName, adminEmail, password, isActive FROM Admins WHERE adminEmail = ?";
    private static final String SELECT_ALL_SQL = "SELECT adminId, adminName, adminEmail, password, isActive  FROM Admins";
    private static final String UPDATE_SQL = "UPDATE Admins SET adminName = ?, adminEmail = ?, password = ?, isActive = ? WHERE adminId = ?";
    private static final String DELETE_SQL = "DELETE FROM Admins WHERE adminId = ?";
    private static final String AdminCredential_sql = "SELECT COUNT(*) FROM Admins WHERE adminEmail = ? AND password = ?";

    private static Connection connection;


    public AdminDAOImpl() {
        try {
            connection = DatabaseConnection.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // TESTED >> WORKING
    @Override
    public boolean isAdminCradentialsValid(String email, String password) {

        try (PreparedStatement stmt = connection.prepareStatement(AdminCredential_sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // TESTED >> WORKING
    @Override
    public boolean addAdmin(Admin admin) {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, admin.getAdminEmail());
            stmt.setString(3, admin.getPassword());
            stmt.setBoolean(4, admin.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        admin.setAdminId(generatedKeys.getLong(1));
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // TESTED >> WORKING
    @Override
    public Admin getAdminById(Long adminId) {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, adminId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdmin(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // TESTED >> WORKING
    @Override
    public Admin getAdminByEmail(String adminEmail) {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            stmt.setString(1, adminEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdmin(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TESTED >> WORKING
    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // TESTED >> WORKING
    @Override
    public boolean updateAdmin(Admin admin) {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, admin.getAdminEmail());
            stmt.setString(3, admin.getPassword());
            stmt.setBoolean(4, admin.isActive());
            stmt.setLong(5, admin.getAdminId());

            int affectedRows = stmt.executeUpdate(); // Execute and get affected row count
            return affectedRows > 0; // Return true if at least one row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false in case of exception
    }
    // TESTED >> WORKING
    @Override
    public boolean deleteAdmin(Long adminId) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, adminId);

            int affectedRows = stmt.executeUpdate(); // Execute and get affected row count
            return affectedRows > 0; // Return true if at least one row was deleted

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false in case of exception
    }

    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getLong("adminId"));
        admin.setAdminName(rs.getString("adminName"));
        admin.setAdminEmail(rs.getString("adminEmail"));
        admin.setPassword(rs.getString("password"));
        admin.setActive(rs.getBoolean("isActive"));
        return admin;
    }
}
