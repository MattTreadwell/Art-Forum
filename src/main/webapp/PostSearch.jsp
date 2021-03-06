<%@ page import="com.webHelper" %>
<%@ page import="DB_util.Database" %>
<%@ page import="DB_util.post" %>
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
<div class="jumbotron post" data-title="<%=p.Title%>">
    <div class="voteButtons">
        <span class="upvote" data-id="<%=p._postId%>"> </span>
        <p class="postScore text-center" id="<%=p._postId%>"><strong><%=p.mPostScore%></strong></p>
        <span class="downvote" data-id="<%=p._postId%>"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle"><%=p.Title%> </h5>
        <p class="postUser">By <a href="profile.jsp?username=<%=p.OwnerName%>"><%=p.OwnerName%></a> <%=webHelper.relativeTime(p.postDate)%></p>
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
            <%
                if(p.mStatus == Database.NSFW) {
            %>
            <img class="postImagePreview postImageBlur" src="<%=p.link%>" alt="image not found" onerror="this.src='img/notfound.png'"/>
            <%
            } else {
            %>
            <img class="postImagePreview" src="<%=p.link%>" alt="image not found" onerror="this.src='img/notfound.png'"/>
            <%
                }
            %>
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
            <a href="viewPost.jsp?postId=<%=p._postId%>" class="btn btn-secondary btn-xs" role="button"><%=webHelper.commentNumber(p.mCommentIds.size())%> Comments</a>
            <%
                if(p.mPostType == Database.IMAGE) {
                    switch (p.mStatus) {
                        case post.NSFW:
            %>
            <button class="btn btn-danger" type="submit">NSFW</button>

            <%
                    break;
                case post.SAFE:
            %>
            <button class="btn btn-success" type="submit">Certified ART</button>
            <%
                    break;
                case post.UNSURE:
            %>
            <button class="btn btn-secondary" type="submit">Unsure</button>

            <%
                    break;
                case post.PROCESSING:
            %>
            <button class="btn btn-warning" type="submit">processing</button>

            <%
                    break;
                case post.ERROR:
            %>
            <button class="btn btn-danger" type="submit">ERROR</button>

            <%
                    }
                }
            %>
        </div>
    </div>
</div></div>

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

<!-- script for upvote buttons -->
<script src="js/vote.js" type="text/javascript"></script>