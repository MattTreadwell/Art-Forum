<%@ page import="DB_util.Database" %>
<%@ page import="com.webHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Pagination retrieval
    String retrieveNum = request.getParameter("index");
    int index = 1;
    if(retrieveNum != null) {
        index = Integer.parseInt(retrieveNum);
    }

    // Session to check if user is logged in
    HttpSession session1 = request.getSession();
    String username = (String) session1.getAttribute("username");
    boolean login = null != username;

    // Make a new Database class

    // Get ArrayList of Posts from db by index #
    //java.util.ArrayList<DB_util.post> postChunk = db.getPostChunk(index);

%>

<!-- THIS IS ONLY FOR TESTING DATABASE INTEGRATION -->

<html class="sr">
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

<div class="text-center no-click" id="load">
    <img class="loadingImage centered" src="img/oragami.gif" alt="loading">
</div>

<div class="container-fluid postBox">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-7 col-xl-5 offset-md-0 offset-sm-0 offset-lg-1 offset-xl-3 load-hidden" id="postCol">

        </div>

        <!-- "Right" div for showing calendar, weathers, other APIs -->
        <div class="col-sm-0 col-md-0 col-lg-3 col-xl-2  d-none d-lg-block">
            <div class="jumbotron load-hidden" id="sidebar">
                <h5>Art Forum is the Front Page of CSCI201</h5>

                <div class="text-center">
                    <a class="btn btn-primary" href="newPost.jsp" role="button"><strong>CREATE POST</strong></a>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- script for upvote buttons -->
<script>
    // Consider adding custom data attributes to the buttons to identify the correct score element
    // https://stackoverflow.com/questions/19380910/jquery-append-jquery-variable-to-a-class-name
    // need to also make an ajax call
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
    
    
    
/*    for (const btn of document.querySelectorAll('.upvote')) {
        btn.addEventListener('click', event => {
            event.target.classList.toggle('on');
        })
    }

    for (const btn of document.querySelectorAll('.downvote')) {
        btn.addEventListener('click', event => {
            event.target.classList.toggle('on');
    })
    }*/
    // TODO integrate with database AND make sure post can be both upvoted and downvoted
    // This will need to be way more complex than just toggling the 'on' class

    // TODO consider merging these into one once functionality is complete
    // Upvote button code
/*    for (const btn of document.querySelectorAll('.upvote')) {
        btn.addEventListener('click', event = > {
            if(event.target.classList.contains("on");)
        {
            // Button is already activated; make this un-upvote
            console.log("class on toggled off");
            event.target.classList.toggle('on');

            //var db = new Packages.DB_util.

        }
    else
        {
            // Button isn't activated; upvote post and undo downvote if it's downvoted
            event.target.classList.toggle('on');
        }
    })
    }

    // Downvote button code (should be an inverse of the previous)
    for (const btn of document.querySelectorAll('.downvote')) {
        btn.addEventListener('click', event = > {
            if(event.target.classList.contains("on");)
        {
            // Button is already activated; make this un-downvote
            console.log("class on toggled off");
            event.target.classList.toggle('on');

        }
    else
        {
            // Button isn't activated; upvote post and undo upvote if it's upvoted
            event.target.classList.toggle('on');
        }
    })
    }*/
</script>

<script>
/*    $("#sidebar").hide(0);
    $("#postCol").hide(0);*/
</script>
<script type="text/javascript">
    window.onload = function loadPosts() {

        console.log("calling loadPosts");
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "PostChunk.jsp?index=<%=index%>");
        xhttp.onreadystatechange = function() {
            $("#load").fadeOut();
            document.getElementById('postCol').innerHTML = xhttp.responseText;
            ScrollReveal().reveal('.jumbotron.post', {delay: 200, reset: false});
            ScrollReveal().reveal('.pageButton', {delay: 200, reset: true});
            ScrollReveal().reveal('#sidebar', {delay: 200, reset: true});
            //ScrollReveal().init();

        };
        xhttp.send();
        document.getElementById('postCol').innerHTML = xhttp.responseText;
        console.log(this.responseText);
        return true;
    }
</script>
</body>
</html>
