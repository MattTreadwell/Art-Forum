<%@ page import="DB_util.Database" %>
<%@ page import="DB_util.post" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static org.apache.logging.log4j.web.WebLoggerContextUtils.getServletContext" %>
<%@ page import="com.webHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String profile = request.getParameter("username");

    if(profile == null) {
        request.setAttribute("error", "Missing user profile");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
        return;
    }

    // Session to check if user is logged in
    HttpSession session1 = request.getSession();
    String username = (String) session1.getAttribute("username");
    boolean login = null != username;

    Database db = new Database();
    System.out.println("getting profile of user: " + profile);
    DB_util.user userProfile = db.getUser(profile);

    if(userProfile == null) {
        request.setAttribute("error", "User " + profile + " not found.");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
        return;
    }



%>

<html>
<head>
    <title>Art Forum</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densityDpi=device-dpi"/>
    <meta name="description"
          content="This is definitely not reddit">

    <!-- Load fonts, libraries, and css -->
    <link rel="stylesheet" href="style.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

</head>
<body>

<div id="particles-js-main"></div>

<!-- Load JS particles -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/particles.js@2.0.0/particles.min.js"></script>

<script>
    particlesJS.load('particles-js-main', 'particles-main.json', function () {
        console.log('particles.json loaded...')
    })
</script>


<!-- Navbar -->
<nav class="navbar navbar-expand-md navbar-light bg-light justify-content-center fixed-top">
    <img class="navIcon d-none d-md-block" src="img/icon.jpg" alt="">
    <a href="index.jsp" class="navbar-brand d-flex w-50 mr-auto"><strong>Art Forum</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse w-100" id="navbarSupportedContent">

        <ul class="navbar-nav w-100 justify-content-center">
            <li>
                <!-- TODO potentially make this nicer looking -->
                <form action="search.jsp" method="get" class="navbar-form zeroMargin" role="search">
                    <div class="input-group">
                        <input type="search" name="query" value="" class="form-control"
                               id="searchForm"
                               aria-describedby="searchHelp" placeholder="Search">
                        <button type="submit" class="btn btn-secondary"><i class="fa fa-search"></i></button>
                    </div>

                </form>
            </li>
        </ul>

        <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
            <%
                if (login) {
            %>
            <li>
                <a class="btn btn-primary" href="profile.jsp?username=<%=username%>" role="button"><%=username%>
                </a>
                <a class="btn btn-outline-primary" href="Logout" role="button">Logout</a>
            </li>
            <%
            } else {
            %>
            <li>
                <a class="btn btn-primary" href="login.jsp" role="button">Login</a>
                <a class="btn btn-outline-primary" href="register.jsp" role="button">Sign Up</a>
            </li>
            <%
                }
            %>
        </ul>

    </div>
</nav>

<div class="container-fluid postBox">
    <div class="row">
        <!-- "Left" div for showing User info, location, score -->
        <div class="col-sm-12 col-md-12 col-lg-4 col-xl-3 offset-md-0 offset-sm-0 offset-lg-1 offset-xl-1">
            <div class="jumbotron">
                <div class="text-center">
                    <h4><strong><%=userProfile.username%></strong></h4>
                    <h5>Post Score: <%=userProfile.profile.postScore%></h5>
<%--
                    <h5>Country: <%=userProfile.profile.country%></h5>
--%>
                </div>
            </div>
        </div>

        <!-- Display User Posts or Comments -->
        <div class="col-sm-12 col-md-12 col-lg-6 col-xl-5">
            <div class="userPosts"></div>
            <!-- dynamic post display -->
            <%
                // Iterate over the array of posts
                for(DB_util.post p : userProfile.userPosts) {
            %>
            <div class="jumbotron post" data-title="<%=p.Title%>">
                <div class="voteButtons">
                    <span class="upvote" data-index="<%=p._postId%>"> </span>
                    <p class="postScore text-center" id="score<%=p.mPostScore%>"><strong><%=p.mPostScore%></strong></p>
                    <span class="downvote" data-index="<%=p._postId%>"> </span>
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
            </div>

            <%
                }
            %>

        </div>

    </div>
</div>
</body>
</html>
