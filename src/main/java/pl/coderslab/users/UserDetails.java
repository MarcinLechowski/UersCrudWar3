package pl.coderslab.users;

import pl.coderslab.entity.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    @WebServlet("/users/details")
    public class UserDetails extends HttpServlet{
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            UserDao userDao = new UserDao();
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("user", userDao.read(id));
            getServletContext().getRequestDispatcher("/users/details.jsp")
                    .forward(request, response);
        }
    }

