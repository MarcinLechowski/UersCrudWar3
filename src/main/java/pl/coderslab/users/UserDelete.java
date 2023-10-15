package pl.coderslab.users;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class UserDelete extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));

        // Przykład: Pobranie obiektu użytkownika z bazy danych
        UserDao userDao = new UserDao();
        User user = userDao.read(userId);

        // Umieść obiekt użytkownika jako atrybut w żądaniu
        req.setAttribute("user", user);

        // Przekieruj na stronę potwierdzenia usuwania
        req.getRequestDispatcher("/users/delete.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserDao userDao = new UserDao();

        userDao.delete(Integer.parseInt(req.getParameter("id")));

        resp.sendRedirect(req.getContextPath() + "/users/list");

    }
    }
