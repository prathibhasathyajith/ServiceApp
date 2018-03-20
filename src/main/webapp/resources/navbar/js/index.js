$(function () {
    var links = $('.sidebar-links > div > a');
    var selectedDiv;
    var atags = $('.sidebar-links > div > ul > li');
    
    links.on('click', function () {
        

        if ($(this).parent().hasClass('selected')) {            
            $(this).parent().removeClass('selected');
        } else {
            links.parents().removeClass('selected');
            $(this).parent().addClass('selected');
        }
    });



});
