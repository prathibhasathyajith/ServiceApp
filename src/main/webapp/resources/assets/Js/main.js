$(document).ready(function () {
    
    //----------------welcome pagge------------------
    
    
//    $(".es-svgElement").mouseenter(function (e) {
//        $("#es-text1").addClass("animation1");
//        $("#es-text2").addClass("animation2");
//    });
//    $(".es-svgElement").mouseleave(function (e) {
//        $("#es-text1").removeClass("animation1");
//        $("#es-text2").removeClass("animation2");
//    });
    $(".es-svgElement").click(function (e) {
//        $("#formES").submit();
    });
    
    //----------------login pagge------------------
    
    
    
    $(".es-fieldContent").mouseenter(function (e) {
        $(".es-label").css({"margin":"-7px 0px 0px 8px",
                           "opacity":"1",
                            "z-index":"0",
                            "font-size": "12px"
                           });
        $(".es-field").css("background","#00000036");
        
    });
    $(".es-buttonContent").click(function (e) {
        $(".es-buttonContent").css({"border":"1px solid white"});
    });

    
});
