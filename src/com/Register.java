package com;

import DB_util.Database;
import DB_util.user;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO finish this
        // Should check if both 'password' and 'checkpassword' match, and if the username is blank
        // On fail: return them to register.jsp with attribute 'errormsg' as the error
        // On success: return user to index.jsp with 'username' attribute set as their username and add the user to the database


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String checkpassword = request.getParameter("checkpassword");
        if(username == null||username.trim().length()==0)
        {
            request.setAttribute("errormsg","Username is blank.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
            dispatch.forward(request, response);
            return;
        }
        username = username.trim();
        if(password == null||password.trim().length()==0||checkpassword == null||checkpassword.trim().length()==0)
        {
            request.setAttribute("errormsg","Password is blank.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
            dispatch.forward(request, response);
            return;
        }
        password = password.trim();
        checkpassword = checkpassword.trim();
        if(!password.equals(checkpassword))
        {
            request.setAttribute("errormsg","Passwords do not match.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
            dispatch.forward(request, response);
            return;
        }

        user user = new user(username,password.hashCode(),"",0,0);
        Database db = new Database();
        String mess = db.addUser(user);
        if(!mess.equals(Database.Success))
        {
            request.setAttribute("errormsg",mess);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/register.jsp");
            dispatch.forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("username",username);
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/index.jsp");
        dispatch.forward(request, response);
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
