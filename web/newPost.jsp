<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>New Post</title>

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
<div id="particles-js-login"></div>


<!-- Load JS particles -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/particles.js@2.0.0/particles.min.js"></script>

<script>
    particlesJS.load('particles-js-login', 'particles-login.json', function () {
        console.log('particles.json loaded...')
    })
</script>


<!-- Navbar -->
<nav class="navbar navbar-expand-md navbar-light bg-light justify-content-center">
    <a href="index.jsp" class="navbar-brand d-flex w-50 mr-auto"><strong>NotReddit</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>


    <div class="collapse navbar-collapse w-100" id="navbarSupportedContent">

        <ul class="navbar-nav w-100 justify-content-center">
            <li>
                <!-- TODO potentially make this nicer looking -->
                <form class="navbar-form" role="search" style="margin: 0px 0px">
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

<div class="newPostBox container-fluid">
    <div class="row">

        <div class="col-sm-12 col-md-12 col-lg-6 offset-md-0 offset-sm-0 offset-lg-3">
            <div class="jumbotron" style="background-color: rgba(255,255,255,0.9) !important">
                <h1>New Post</h1>
                <form action="/createPost" method="post">
                    <div class="form-group">
                        <label for="exampleInputUsername1">Post Title</label>
                        <input name="username" type="text" class="form-control" id="exampleInputUsername1"
                               aria-describedby="usernameHelp" placeholder="Enter Title">
                    </div>
                    <div class="form-group">
                        <label for="exampleFormControlSelect1">Post Type</label>
                        <select class="form-control" id="exampleFormControlSelect1" name="SelectBox">
                            <option>Select your option</option>
                            <option value="textForm">Text</option>
                            <option value="linkForm">Link</option>
                            <option value="imageForm">Image</option>
                        </select>
                    </div>
                    <!-- Text Post Form -->
                    <div class="form-group toggle" id="textForm">
                        <label for="exampleFormControlTextarea1">Text</label>
                        <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                    <!-- Link/Image Form -->
                    <div class="form-group toggle" id="linkForm">
                        <label for="linkInput">URL</label>
                        <input type="url" pattern="https://.*" class="form-control" id="linkInput" placeholder="https://notreddit.com">
                    </div>
                    <div class="toggle" id="imageForm">
                        <div class="form-group">
                            <label for="imageInput">Image Link</label>
                            <input type="url" pattern="https://.*" class="form-control" id="imageInput" placeholder="hi.png">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>


    </div>
</div>


<!-- https://stackoverflow.com/questions/5692135/show-visible-hidden-not-show-hide-html-element-based-on-form-select-box-select -->
<script>
    $('.toggle').hide();//Hide all by default


    $('select').on('change', function() {
        $('.toggle').hide();//Hide all
        $("#" + $(this).children("option:selected").val()).show();
    });
</script>


</body>
</html>