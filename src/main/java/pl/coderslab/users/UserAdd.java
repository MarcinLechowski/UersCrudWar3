package pl.coderslab.users;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/add")
public class UserAdd extends HttpServlet {

    private UserDao userDao = new UserDao();
    private User user = new User();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/add").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user.setUserName(req.getParameter("userName"));

        user.setEmail(req.getParameter("userEmail"));

        user.setPassword(req.getParameter("userPassword"));

        userDao.CreateNewUser(user);

        resp.sendRedirect(req.getContextPath() + "/user/list");
    }
}
