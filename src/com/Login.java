package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO finish this
        // Should check check the user's password and username if they are in the database
        // On fail: return them to login.jsp with attribute 'errormsg' as the error
        // On success: return user to index.jsp with 'username' attribute set as their username
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DO NOT ALLOW USERS TO LOGIN VIA POST (leave blank)
    }
}
