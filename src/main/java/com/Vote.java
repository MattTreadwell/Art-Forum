package com;

import DB_util.Database;
import DB_util.post;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Vote", value="/Vote")
public class Vote extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postIdString = request.getParameter("postId");
        ObjectId postId = new ObjectId(postIdString);
        Database db = new Database();

        if(request.getParameter("upvote") != null) {
            db.IncPostScore(postId, 1);
        } else if(request.getParameter("downvote") != null) {
            db.IncPostScore(postId, -1);
        } else {
            System.out.println("Couldn't determine post type");
        }

        post p = db.getPostById(postId);

        // Respond with new score
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<strong>" + p.mPostScore + "</strong>");
    }
}
