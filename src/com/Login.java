package com;

import DB_util.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO finish this
        // Should check check the user's password and username if they are in the database
        // On fail: return them to login.jsp with attribute 'errormsg' as the error
        // On success: return user to index.jsp with 'username' attribute set as their username
        String username = request.getParameter("username");
        if(username == null||username.trim().length()==0)
        {
            request.setAttribute("errormsg","Username is blank.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/login.jsp");
            dispatch.forward(request, response);
            return;
        }
        username = username.trim();
        String password = request.getParameter("password");
        if(password == null||password.trim().length()==0)
        {
            request.setAttribute("errormsg","Password is blank.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/login.jsp");
            dispatch.forward(request, response);
            return;
        }
        password = password.trim();
        Database db = new Database();
        String mess = db.Validate(username,password.hashCode());
        if(!mess.equals(Database.Success))
        {
            request.setAttribute("errormsg",mess);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/login.jsp");
            dispatch.forward(request, response);
            return;
        }
        HttpSession session1 = request.getSession(true);
        session1.setAttribute("username", username);
        request.setAttribute("success", "Successfully logged in as " + username);
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DO NOT ALLOW USERS TO LOGIN VIA POST (leave blank)
    }
}
