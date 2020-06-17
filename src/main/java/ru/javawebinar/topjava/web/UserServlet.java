package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.javawebinar.topjava.web.SecurityUtil.setAuthId;

public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug("action {}", action);
        String userId = request.getParameter("userId");
        if (action == null && userId != null) {
            setAuthId(Integer.parseInt(userId));
            request.getRequestDispatcher("meals?action=all").forward(request, response);
        }
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
