<%@ page import="com.webHelper" %>
<%@ page import="DB_util.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Pagination retrieval
    String retrieveNum = request.getParameter("index");
    int index = 1;
    if(retrieveNum != null) {
        index = Integer.parseInt(retrieveNum);
    }
    // Make a new Database class
    Database db = new Database();
    db.getPostSize();
    // Get ArrayList of Posts from db by index #
    DB_util.post p = db.getLatestPost();
    if(p!=null) {
%>
<div class="jumbotron post" data-title="<%=p.Title%>">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong><%=p.mPostScore%></strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle"><%=p.Title%> MULTITHREAD</h5>
        <p class="postUser">By <a href="profile.jsp?user=<%=p.ownerId%>"><%=p.OwnerName%></a> <%=com.webHelper.relativeTime(p.postDate)%></p>
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
            <img class="postImagePreview" src="<%=p.link%>" alt="image not found"
                 onerror="this.src='img/notfound.png'"/>
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
        </div>
    </div>
</div>

<%
    
} else {
%>
<div class="jumbotron post">
    <div class="text-center">
        <h1>No more posts.</h1>
    </div>
</div>
<%
    }
%>


<script>
    $(".upvote").click(function () {
        var upvoted = $(this).hasClass("on");
        $(this).toggleClass("on");
        console.log($(this));
        // Need to modify associated number
        if(upvoted) {
        } else {
        }
    });
    $(".downvote").click(function () {
        var downvoted = $(this).hasClass("on");
        $(this).toggleClass("on");
        if(downvoted) {
        } else {
        }
    })
</script>
