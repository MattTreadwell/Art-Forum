<%@ page import="DB_util.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Retrieve parameter post ID
    String id = (String) request.getParameter("id");

    // Session to check if user is logged in
    HttpSession session1 = request.getSession();
    String username = (String) session1.getAttribute("username");
    boolean login = null != username;

    // Make a new Database class
    //Database db = new Database();

    // Check if post exists
    // db.getPostById(id);

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
    <script src="https://unpkg.com/scrollreveal"></script>
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
                if(login) {
            %>
            <li>
                <a class="btn btn-primary" href="profile.jsp" role="button"><%=username%></a>
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
        <div class="col-sm-12 col-md-12 col-lg-7 col-xl-5 offset-md-0 offset-sm-0 offset-lg-1 offset-xl-3">
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
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vitae consectetur sapien. Sed
                        vitae pellentesque ex. Aenean et dignissim justo.</p>
                    <div class="btn-group-xs">
                        <button class="btn btn-light btn-xs">420 Comments</button>
                    </div>
                </div>

                <div class="postSeparator"></div>

                <form action="CreateComment" method="post">
                    <div class="form-group">
                        <textarea class="form-control" rows="5" id="comment" placeholder="Write your comment here . . ."></textarea>
                    </div>
                    <div class="text-right">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>

            <!-- Comments go here -->
            <div class="jumbotron post">
                <div class="comment">
                    <div class="voteButtons">
                        <span class="upvote"> </span>
                        <p class="postScore text-center"><strong>69</strong></p>
                        <span class="downvote"> </span>
                    </div>

                    <div class="commentContent">
                        <a href="profile.jsp">Patrick Star</a>
                        <p>Feelsbadman.jpg</p>
                    </div>
                </div>
            </div>

            <div class="jumbotron post">
                <div class="comment">
                    <div class="voteButtons">
                        <span class="upvote"> </span>
                        <p class="postScore text-center"><strong>382</strong></p>
                        <span class="downvote"> </span>
                    </div>

                    <div class="commentContent">
                        <a href="profile.jsp">Spongebobu</a>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vitae euismod lorem. Vestibulum
                            iaculis finibus augue laoreet imperdiet. Interdum et malesuada fames ac ante ipsum primis in
                            faucibus. Etiam eu diam risus. Pellentesque nibh orci, efficitur eu dignissim eget,
                            elementum non sapien. Nam tempor diam id nisl rutrum hendrerit. Nunc sagittis sit amet
                            mauris elementum aliquet. Donec fermentum molestie leo ut sodales.</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- "Right" div for showing other post previews from the user -->
        <div class="col-sm-0 col-md-0 col-lg-3 col-xl-2  d-none d-lg-block">
            <div class="jumbotron">
                <h5>Art Forum is the Front Page of CSCI201</h5>

                <div class="text-center">
                    <a class="btn btn-primary" href="newPost.jsp" role="button"><strong>CREATE POST</strong></a>
                </div>
            </div>
        </div>

    </div>
</div>

<script>
    ScrollReveal().reveal('.jumbotron', {reset: false, delay: 200});
</script>

</body>
</html>
