package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateComment", value = "/CreateComment")
public class CreateComment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO finish this
        // This allows a user to make a comment
        // First check if a user is logged in by retrieving the 'username' session variable (otherwise redirect to error page)
        // If an error occurs, redirect to error.jsp with attribute 'errormsg' set to the error
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
