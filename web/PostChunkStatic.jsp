<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
%>
<!-- Text post template -->
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>69</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a text post!</h5>
        <!-- THIS IS WHAT WILL DIFFER BETWEEN TEXT TYPES (the preview) -->
        <p class="postTextPreview">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vitae
            consectetur sapien. Sed vitae pellentesque ex. Aenean et dignissim justo. Duis maximus risus nec
            purus ullamcorper, non sollicitudin odio molestie.</p>
        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">420 Comments</button>
        </div>
    </div>
</div>
<!-- Link post template  -->
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>1337</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a link post!</h5>
        <p class="postLinkPreview">
            <a href="https://www.reddit.com/">https://www.reddit.com/</a>
        </p>
        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">5.7k Comments</button>
        </div>
    </div>
</div>
<!-- Image post template -->
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>1324</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a normal 16:9 image post!</h5>
        <div class="text-center">
            <img class="postImagePreview" src="img/normalimage.jpg"/>
        </div>

        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">7.7k Comments</button>
        </div>
    </div>
</div>
<!-- Odd image sizes -->
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>1324</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a (fairly) square 1:1 image post!</h5>
        <div class="text-center">
            <img class="postImagePreview" src="img/squareimage.jpg"/>
        </div>

        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">7.7k Comments</button>
        </div>
    </div>
</div>
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>1324</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a tall image post!</h5>
        <div class="text-center">
            <img class="postImagePreview" src="img/tallimage.png"/>
        </div>

        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">7.7k Comments</button>
        </div>
    </div>
</div>
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>1324</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a GIF post!</h5>
        <div class="text-center">
            <img class="postImagePreview" src="img/giftest.gif"/>
        </div>

        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">7.7k Comments</button>
        </div>
    </div>
</div>
<div class="jumbotron post">
    <div class="voteButtons">
        <span class="upvote"> </span>
        <p class="postScore text-center"><strong>1324</strong></p>
        <span class="downvote"> </span>
    </div>
    <div class="postPreview">
        <h5 class="postTitle">This is a broken image post!</h5>
        <div class="text-center">
            <img class="postImagePreview" src="invalid" alt="image not found"
                 onerror="this.src='img/notfound.png'"/>
        </div>

        <div class="btn-group-xs">
            <button class="btn btn-secondary btn-xs">7.7k Comments</button>
        </div>
    </div>
</div>
