<%@ page import="com.webHelper" %>
<%@ page import="DB_util.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    // Make a new Database class
    Database db = new Database();


    // Get ArrayList of Posts from db by index #
    java.util.ArrayList<DB_util.post> results = db.searchPost(request.getParameter("query"));
    // Iterate over the array of posts
    if(!results.isEmpty()) {
        for(DB_util.post p : results) {
%>
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong><%=p.mPostScore%></strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle"><%=p.Title%> </h5>
        <p class="postUser">By <a href="profile.jsp?username=<%=p.OwnerName%>"><%=p.OwnerName%></a></p>
        <!-- THIS IS WHAT WILL DIFFER BETWEEN POST TYPES (the preview) -->
        <%
            switch (p.mPostType) {
                //
                case 1 :
        %>
        <!-- TODO need shortener function -->
        <p class="postTextPreview"><%=p.postContent%></p>
        <%


                break;
            case 2:
        %>
        <!-- TODO consider shortener here too -->
        <p class="postLinkPreview">
            <a href="<%=p.link%>"><%=p.link%></a>
        </p>
        <%
                break;
            case 3:
        %>
        <!-- TODO consider also showing link -->
        <div class="text-center">
            <img class="postImagePreview" src="<%=p.link%>"/>
        </div>
        <%
                break;
            default:
                // Error case: no post type (should probably skip)
        %>
        <p class="postTextPreview text-danger">ERROR: MISSING/INVALID POST TYPE</p>
        <%
                    break;
            }
        %>
        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs"><%=webHelper.commentNumber(p.mComments.size())%> Comments</button>
        </div>
    </div>
</div>

<%
    }
} else {
%>
<div class="jumbotron post">
    <div class="text-center">
        <h1>No matching posts found.</h1>
    </div>
</div>
<%
    }
%>