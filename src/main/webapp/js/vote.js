function ajaxGetChunk() {
    $.ajax({
        url: "PostChunk.jsp?index=<%=index%>",
        type: "GET",
        async: true,
        success: function (response) {
            $("#load").fadeOut();
            $("#postCol").html(response);
            ScrollReveal().reveal('.jumbotron.post', {delay: 200, reset: false});
            ScrollReveal().reveal('.pageButton', {delay: 200, reset: false});
            ScrollReveal().reveal('#sidebar', {delay: 200, reset: true});
        }
    })
}

function ajaxUp(id) {
    console.log("calling upvote ajax");
    $.ajax({
        url: "Vote?postId=" + id + "&upvote=yes",
        type: "GET",
        async: true,
        success: function (response) {
            console.log("calling upvote ajax succeed");
            location.reload()
        }
    })
}

function ajaxDown(id) {
    console.log("calling downvote ajax");
    $.ajax({
        url: "Vote?postId=" + id + "&downvote=yes",
        type: "GET",
        async: true,
        success: function (response) {
            console.log("calling downvote ajax succeed");
            location.reload();
        }
    })
}

// Consider adding custom data attributes to the buttons to identify the correct score element
// https://stackoverflow.com/questions/19380910/jquery-append-jquery-variable-to-a-class-name
// need to also make an ajax call
$(".upvote").click(function () {
    var upvoted = $(this).hasClass("on");
    $(this).toggleClass("on");

    // Need to modify associated number
    if(upvoted) {
        ajaxDown($(this).data('id'));
    } else {
        ajaxUp($(this).data('id'));
    }
});

$(".downvote").click(function () {
    var downvoted = $(this).hasClass("on");
    $(this).toggleClass("on");

    if(downvoted) {
        ajaxUp($(this).data('id'));
    } else {
        ajaxDown($(this).data('id'));
    }
});