package com;

import DB_util.Database;
import DB_util.post;
import ML.ImageProcess;
import org.bson.types.ObjectId;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreatePost", value = "/CreatePost")
public class CreatePost extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        if (title == null || title.trim().length() == 0) {
            request.setAttribute("errormsg", "Title is blank.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/newPost.jsp");
            dispatch.forward(request, response);
            return;
        }
        String selection = request.getParameter("SelectBox");
        String input;
        if (selection.equals("textForm")) {
            input = request.getParameter("text");
        } else if (selection.equals("linkForm")) {
            input = request.getParameter("link");
        } else {
            input = request.getParameter("imagelink");
        }
        if (input == null || input.trim().length() == 0) {
            request.setAttribute("errormsg", "Input is blank.");
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/newPost.jsp");
            dispatch.forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Database db = new Database();
        post post;
        if (selection.equals("textForm")) {
            post = new post(title, username, input, "", Database.TEXT);
        } else if (selection.equals("linkForm")) {
            post = new post(title, username, "", input, Database.LINK);
        } else {
            post = new post(title, username, "", input, Database.IMAGE);
        }
        // Need to retrieve the ID of the post from database (otherwise it's null)
        ObjectId id = db.addPost(post);
        // Invoke Multi-threaded call to computer vision code
        if (post.mPostType == Database.IMAGE) {
            System.out.println("Post ID: " + post._postId);
            ImageProcess ip = (ImageProcess) getServletContext().getAttribute("ip");

            ip.queuePost(db.getPostById(id));
        }

        response.sendRedirect("/index.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
