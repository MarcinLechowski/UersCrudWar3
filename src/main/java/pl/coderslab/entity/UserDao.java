package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    private static final String Create_New_User = "INSERT INTO users(user_name, email, password) VALUES (?, ?, ?);";
    private static final String Select_User_By_Id = "SELECT * FROM users WHERE id = ?;";
    private static final String Select_User_By_Name = "SELECT * FROM users WHERE user_name = ?;";
    private static final String Select_User_By_Email = "SELECT * FROM users WHERE email = ?;";
    private static final String Select_All_Users = "SELECT * FROM users;";
    private static final String Update_User = "UPDATE users SET email = ?, user_name = ?, password = ? WHERE id = ?;";
    private static final String Delete_User = "DELETE FROM users WHERE id = ?;";
    private User[] userList;
    private int index;

    public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }


    public User CreateNewUser(Connection conn, User user) {

        try (PreparedStatement statement = conn.prepareStatement(Create_New_User, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());

            String hashedPassword = hashPassword(user.getPassword());
            statement.setString(3, hashedPassword);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                System.out.println("Nowy użytkownik został dodany.");
                System.out.println("ID nowego użytkownika: " + user.getId());
            } else System.out.println("Dodawanie nowego użytkownika nie powiodło się");
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User SelectUserById(Connection conn, int userId) {

        User user = null;
        try {
            PreparedStatement statement = conn.prepareStatement(Select_User_By_Id);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));

                System.out.println("User ID: " + user.getId());
                System.out.println("Username: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());
                rs.close();
                statement.close();
            } else System.out.println("Brak użytkownika o podanym ID: " + userId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User SelectUserByName(Connection conn, String userName) {
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(Select_User_By_Name);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));

                System.out.println("User ID: " + user.getId());
                System.out.println("Username: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());
                rs.close();
                ps.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User SelectUserByEmail(Connection conn, String userEmail) {

        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(Select_User_By_Email);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                System.out.println("User ID: " + user.getId());
                System.out.println("Username: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());

                rs.close();
                ps.close();

            } else System.out.println("Brak użytkowanika o podanym adresie email");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User[] SelectAllUsers(Connection conn) {
        try {
            PreparedStatement statement = conn.prepareStatement(Select_All_Users);
            ResultSet rs = statement.executeQuery();

            // Obliczsnie liczby rekordów w ResultSet
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            rs.close();
            statement.close();

            // Ponownie wykonaj zapytanie i przetwórz wyniki
            User[] users = new User[rowCount];
            statement = conn.prepareStatement(Select_All_Users);
            rs = statement.executeQuery();

            int index = 0;
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users[index++] = user;

                System.out.println("User ID: " + user.getId());
                System.out.println("Username: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());
                System.out.println();
            }

            rs.close();
            statement.close();

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(Update_User);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            String hashedPassword = hashPassword(user.getPassword());
            statement.setString(3, hashedPassword);
            statement.setInt(4, user.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Dane użytkownika zostały zaktualizowane.");
            } else {
                System.out.println("Brak użytkownika o podanym ID: " + user.getId());
            }

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int userId) {

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(Delete_User);
            statement.setInt(1, userId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Użytkownik o ID " + userId + " został usunięty z bazy danych.");
            } else {
                System.out.println("Brak użytkownika o podanym ID: " + userId);
            }

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User SelectUserById(int i) {
        return null;
    }

    public void CreateNewUser(User user) {
    }

    public User[] SelectAllUsers() {
        return null;
    }
}


