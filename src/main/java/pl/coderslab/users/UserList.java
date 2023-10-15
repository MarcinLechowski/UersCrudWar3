package pl.coderslab.users;

import pl.coderslab.entity.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/list")
    public class UserList extends HttpServlet {
    private UserDao userDao = new UserDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   request.setAttribute("users", userDao.findAll());
        System.out.println(userDao.findAll());
        getServletContext().getRequestDispatcher("/users/list.jsp").forward(request, response);

    }


}
