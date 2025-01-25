import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public interface UserDAO {
    boolean insertUser(User user);
    User getUserById(long userId);
    User getUserByPhone(String phone);
    User getUserByEmail(String email);
    boolean updateUser(User user);
    boolean deleteUser(long userId);
}

public class UserDAOImpl implements UserDAO {
    private Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertUser(User user) {
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
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
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

    // Implement other methods similarly...
}