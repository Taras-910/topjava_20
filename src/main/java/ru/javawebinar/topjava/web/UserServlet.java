package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Autowired
    private AdminRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controller = new AdminRestController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.info("doPost");
        String id = request.getParameter("id");
        User user = new User(id.isEmpty() ? null : Integer.parseInt(id), request.getParameter("name"),
                request.getParameter("email"), request.getParameter("password"), Role.USER);

        log.debug("user {}",user.toString());
        log.info(user.isNew() ? "Create {}" : "Update {}", user);
        if (user.isNew()) {
            controller.create(user);
        } else {
            controller.update(user, getId(request));
        }
        response.sendRedirect("users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug("action {}",action);
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("users");
                break;
            case "byEmail":
                String email = request.getParameter("email");
                final User userByEmail = controller.getByMail(email);
                request.setAttribute("user", userByEmail);
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
            case "create":
                log.info("case create");
            case "update":
                final User user = "create".equals(action) ?
                        new User(null, "", "", "", Role.USER) :
                        controller.get(getId(request));
                log.info("user {}",user);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("users", controller.getAll());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }
    }
    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
