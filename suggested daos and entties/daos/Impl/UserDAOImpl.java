import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addNewUser(User user) {
        String sql = "INSERT INTO Users (phone, displayName, userEmail, password, gender, country, birthday, bio, userStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getPhone());
            stmt.setString(2, user.getDisplayName());
            stmt.setString(3, user.getUserEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getCountry());
            stmt.setDate(7, java.sql.Date.valueOf(user.getBirthday()));
            stmt.setString(8, user.getBio());
            stmt.setString(9, user.getUserStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getLong(1));
                        System.out.println("User inserted successfully with ID: " + user.getUserId());
                        return true;
                    }
                }
            } else {
                System.out.println("Failed to insert user. No rows affected.");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(long userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("userId"));
                user.setPhone(rs.getString("phone"));
                user.setDisplayName(rs.getString("displayName"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setCountry(rs.getString("country"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByPhone(String phone) {
        String sql = "SELECT * FROM Users WHERE phone = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("userId"));
                user.setPhone(rs.getString("phone"));
                user.setDisplayName(rs.getString("displayName"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setCountry(rs.getString("country"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserStatusById(long userId) {
        String sql = "SELECT userStatus FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  return rs.getString("userStatus");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getUserDisplayNameById(long userId) {
        String sql = "SELECT displayName FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  return rs.getString("displayName");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public List<User> getUsersByCountry(String country) {
        String sql = "SELECT * FROM Users WHERE country = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday").toLocalDate());
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getUsersByStatus(String status) {
        String sql = "SELECT * FROM Users WHERE userStatus = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday").toLocalDate());
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getUsersByGender(String gender) {
        String sql = "SELECT * FROM Users WHERE gender = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gender);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setPhone(rs.getString("phone"));
                    user.setDisplayName(rs.getString("displayName"));
                    user.setUserEmail(rs.getString("userEmail"));
                    user.setPassword(rs.getString("password"));
                    user.setGender(rs.getString("gender"));
                    user.setCountry(rs.getString("country"));
                    user.setBirthday(rs.getDate("birthday").toLocalDate());
                    user.setBio(rs.getString("bio"));
                    user.setUserStatus(rs.getString("userStatus"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("userId"));
                user.setPhone(rs.getString("phone"));
                user.setDisplayName(rs.getString("displayName"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setCountry(rs.getString("country"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                user.setBio(rs.getString("bio"));
                user.setUserStatus(rs.getString("userStatus"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET phone = ?, displayName = ?, userEmail = ?, password = ?, gender = ?, country = ?, birthday = ?, bio = ?, userStatus = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getPhone());
            stmt.setString(2, user.getDisplayName());
            stmt.setString(3, user.getUserEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getCountry());
            stmt.setDate(7, java.sql.Date.valueOf(user.getBirthday()));
            stmt.setString(8, user.getBio());
            stmt.setString(9, user.getUserStatus());
            stmt.setLong(10, user.getUserId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserProfileById(long userId) {
        String sql = "UPDATE Users SET phone = ?, displayName = ?, userEmail = ?, gender = ?, country = ?, birthday = ?, bio = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "1234567890");
            stmt.setString(2, "New Display Name");
            stmt.setString(3, "example@gmail.com");
            stmt.setString(4, "Male");
            stmt.setString(5, "New country");
            stmt.setString(6, "20-2-2002");
            stmt.setString(3, "New Bio");
            stmt.setLong(4, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserPasswordById(long userId) {
        String sql = "UPDATE Users SET password = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "New password");
            stmt.setLong(2, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUserById(long userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId); 
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
