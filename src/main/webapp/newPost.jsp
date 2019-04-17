<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Session to check if user is logged in
    HttpSession session1 = request.getSession();
    String username = (String) session1.getAttribute("username");
    boolean login = null != username;
    if(!login)
    {
        request.setAttribute("errormsg","You are not logged in.");
        RequestDispatcher dispatch = request.getRequestDispatcher("/login.jsp");
        dispatch.forward(request, response);
    }
    String error = (String)request.getAttribute("errormsg");
    if(error == null)
    {
        error = "";
    }
    // Redirect users to login page
%>

<html>
<head>
    <title>New Post</title>

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densityDpi=device-dpi"/>
    <meta name="description"
          content="This is definitely not reddit">

    <!-- Load fonts, libraries, and css -->
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="dropzone.css">
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
    <script type="text/javascript" src="./js/dropzone.js"></script>


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

<div class="newPostBox container-fluid">
    <div class="row">

        <div class="col-sm-12 col-md-12 col-lg-6 offset-md-0 offset-sm-0 offset-lg-3">
            <div class="jumbotron" style="background-color: rgba(255,255,255,0.9) !important">
                <h1>New Post</h1>
                <p><%=error%></p>
                <form action="CreatePost" method="post">
                    <div class="form-group">
                        <label for="exampleInputUsername1">Post Title</label>
                        <input name="title" type="text" class="form-control" id="exampleInputUsername1"
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
                        <textarea name="text" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                    <!-- Link Form -->
                    <div class="form-group toggle" id="linkForm">
                        <label for="linkInput">URL</label>
                        <input name="link" type="url" pattern="https://.*" class="form-control" id="linkInput"
                               placeholder="https://Art Forum.com">
                    </div>
                    <!-- Image Form -->
                    <div class="toggle" id="imageForm">
                        <div class="form-group">
                            <label for="imageInput">Image Link</label>
                            <input name="imagelink" type="url" pattern="https://.*" class="form-control" id="imageInput"
                                   placeholder="hi.png">
                            <div class="text-center">
                                <p><strong>or</strong></p>
                            </div>
                            <label for="image-upload">Image Upload</label>
                            <!-- Dropzone Imgur uploader -->
                            <div class="dropzone needsclick" id="image-upload">
                                <div class="dz-message needsclick">
                                    Drop an image here or click to upload.<BR>
                                    <span class="note needsclick">(files are uploaded to imgur)</span>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="text-center" id="submitbutton">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </form>

            </div>
        </div>


    </div>
</div>


<!-- https://stackoverflow.com/questions/5692135/show-visible-hidden-not-show-hide-html-element-based-on-form-select-box-select -->
<script>
    $('.toggle').hide();//Hide all by default
    $('#submitbutton').hide();

    $('select').on('change', function () {
        $('#submitbutton').fadeIn();
        $('.toggle').hide();//Hide all
        $("#" + $(this).children("option:selected").val()).show();
    });
</script>


<script>
    Dropzone.autoDiscover = false;

    var myDropzone = new Dropzone("div#image-upload", {
        maxFiles: 1, // only allow one image per post (no albums)
        url: "https://api.imgur.com/3/image", //imgur endpoint for image upload
        paramName: "image", //important for imgur request name
        acceptedFiles: "image/jpeg,image/png", //only allow image files to be uploaded
        method: "post",
        headers: {
            'Cache-Control': null, //required for cors
            'X-Requested-With': null, //required for cors
            'Authorization': "Client-ID 2f9f1077add92ee" //replace YOUR_CLIENT_ID with the one obtained from imgur
        },
        init: function () {
            this.on("success", function (file, response) {
                // Called after the file successfully uploaded.
                var field = document.getElementById("imageInput");
                //console.log(response);
                field.value = response.data.link;
                console.log("uploaded image");

            });
            this.on("addedfile", function(file) {

            });
            this.on("sending", function(file, xhr, data) {

            });
            this.on("maxfilesexceeded", function(file){
                alert("Only one image is allowed per post!");
            });
        }



    });


</script>

</body>
</html>
