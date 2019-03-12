<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Display success message upon user login
    String success = (String) request.getAttribute("success");
    if (success == null) {
        success = "";
    }
    // Session to check if user is logged in
    HttpSession session1 = request.getSession();
%>

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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

</head>
<body>

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

<div class="postBox container-fluid">
    <div class="row">
<%--        <div class="col-sm-0 col-md-2 col-lg-3">
            <div class="jumbotron">

            </div>
        </div>--%>
        <!-- Main div with posts -->
        <div class="col-sm-12 col-md-12 col-lg-5 offset-md-0 offset-sm-0 offset-lg-2">
            <div class="jumbotron">

            </div>
            <div class="jumbotron">

            </div>
            <div class="jumbotron">

            </div>
            <div class="jumbotron">

            </div>
        </div>

        <!-- "Right" div for showing calendar, weathers, other APIs -->
        <div class="col-sm-0 col-md-0 col-lg-3">
            <div class="jumbotron">
                <h3>NotReddit is the Front Page of CSCI201</h3>

                <div class="text-center">
                    <a class="btn btn-primary" href="login.jsp" role="button"><strong>CREATE POST</strong></a>
                </div>
            </div>
        </div>

    </div>
</div>


</body>
</html>
