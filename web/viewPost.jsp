<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>NotReddit</title>
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
<nav class="navbar navbar-expand-md navbar-light bg-light justify-content-center">
    <img class="navIcon d-none d-md-block" src="img/icon.jpg" alt="">
    <a href="index.jsp" class="navbar-brand d-flex w-50 mr-auto"><strong>NotReddit</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse w-100" id="navbarSupportedContent">

        <ul class="navbar-nav w-100 justify-content-center">
            <li>
                <!-- TODO potentially make this nicer looking -->
                <form action="/Search" method="get" class="navbar-form zeroMargin" role="search">
                    <div class="input-group">
                        <input type="search" name="searchQuery" value="" class="form-control"
                               id="searchForm"
                               aria-describedby="searchHelp" placeholder="Search">
                        <button type="submit" class="btn btn-secondary"><i class="fa fa-search"></i></button>
                    </div>

                </form>
            </li>
        </ul>

        <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
            <li>
                <a class="btn btn-primary" href="login.jsp" role="button">Login</a>
                <a class="btn btn-outline-primary" href="register.jsp" role="button">Sign Up</a>
            </li>
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
                    <!-- THIS IS WHAT WILL DIFFER BETWEEN POST TYPES (the preview) -->
                    <p>What the fuck did you just fucking say about me, you little bitch? I'll have you know I graduated top of my class in the Navy Seals, and I've been involved in numerous secret raids on Al-Quaeda,</p>
                    <div class="btn-group-xs">
                        <button class="btn btn-secondary btn-xs">420 Comments</button>
                    </div>
                </div>
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

                <div class="comment">
                    <div class="voteButtons">
                        <span class="upvote"> </span>
                        <p class="postScore text-center"><strong>382</strong></p>
                        <span class="downvote"> </span>
                    </div>

                    <div class="commentContent">
                        <a href="profile.jsp">Spongebobu</a>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vitae euismod lorem. Vestibulum iaculis finibus augue laoreet imperdiet. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam eu diam risus. Pellentesque nibh orci, efficitur eu dignissim eget, elementum non sapien. Nam tempor diam id nisl rutrum hendrerit. Nunc sagittis sit amet mauris elementum aliquet. Donec fermentum molestie leo ut sodales.</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- "Right" div for showing calendar, weathers, other APIs -->
        <div class="col-sm-0 col-md-0 col-lg-3 col-xl-2  d-none d-lg-block">
            <div class="jumbotron">
                <h5>NotReddit is the Front Page of CSCI201</h5>

                <div class="text-center">
                    <a class="btn btn-primary" href="newPost.jsp" role="button"><strong>CREATE POST</strong></a>
                </div>
            </div>
        </div>

    </div>
</div>


</body>
</html>
