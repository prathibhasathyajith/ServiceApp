
$(document).ready(function () {
    $(".coner").click(function (e) {
        $(".dropdown").toggle(100);
        e.stopPropagation();
    });
    $("html").click(function (e) {
        $(".dropdown").fadeOut(100);
    });
    $(".headerBut").click(function (e) {
        e.stopPropagation();
    });
    $(".nav-bar > ul >li a").click(function () {
        window.localStorage.setItem("item", $(this).attr("id"));
        $(".nav-bar > ul >li a").removeClass("active");
        $(this).addClass("active");
    });


    var id = window.localStorage.getItem("item");
    $("#" + id).addClass("active");

    

});
