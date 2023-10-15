package pl.coderslab.entity;


import org.mindrot.jbcrypt.BCrypt;


import java.sql.*;
import java.util.ArrayList;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    private static final String FIND_ALL_USERS_QUERY =
            "select * from users order by id desc ";

    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?;";

    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";

    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? where id = ?";

    public void update(User user) {
        try (Connection conn = utils.DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, this.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User read(int userId) {
        try (Connection conn = utils.DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> findAll() {
        try (Connection conn = utils.DbUtil.getConnection()) {
            ArrayList<User> usersArray = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                usersArray.add(user);
            }
            return usersArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User create(User user) {
        try (Connection conn = utils.DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        try (Connection conn = utils.DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas usuwania użytkownika", e);
        }
    }



    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
