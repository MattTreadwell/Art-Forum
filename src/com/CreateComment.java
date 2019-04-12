package com;

import DB_util.comment;
import org.bson.types.ObjectId;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateComment", value = "/CreateComment")
public class CreateComment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO finish this
        // This allows a user to make a comment
        // First check if a user is logged in by retrieving the 'username' session variable (otherwise redirect to error page)
        // If an error occurs, redirect to error.jsp with attribute 'errormsg' set to the error
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        if(username==null)
        {
            request.setAttribute("errormsg","You are not logged in.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
            return;
        }
        String temp = request.getParameter("postId");
        ObjectId postId = new ObjectId(temp);
        String comment = request.getParameter("comment");
        DB_util.comment cm = new comment(comment,username,postId);

    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//    {
//
//    }
}
