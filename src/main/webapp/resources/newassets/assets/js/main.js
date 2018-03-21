//===================== navbar js =============================

$(document).ready(function () {
    var link = localStorage.getItem('link');
    var sublink = localStorage.getItem('sublink');
    $('#' + sublink).addClass('active');
    $('#' + link).addClass('selected');
});

$(function () {
    var links_1 = $('.sidebar-links > div > a');
    var links_2 = $('.sidebar-links > div > ul > li');

    links_1.on('click', function () {

        if ($(this).parent().hasClass('selected')) {
            if ($(this).next().children().hasClass('active')) {
                $(this).css({
                    'background-color': '#262626',
                    'color': 'green'
                });

                links_1.parents().removeClass('selected');
            } else {
                $(this).parent().removeClass('selected');
            }

        } else {
            for (var i = 0; i < links_1.parents().length; i++) {
                if (links_1.eq(i).parents().hasClass('selected')) {
                    var foo = false;
                    if (links_1.eq(i).next().children().hasClass('active')) {
                        foo = true;
                    }
                    if (!foo) {
                        links_1.eq(i).parents().removeClass('selected');
                    }

                }
            }
            $(this).parent().addClass('selected');
            console.log($(this).parent()[0].id);
            localStorage.setItem('link', $(this).parent()[0].id);
        }
    });

    links_2.on('click', function () {
        links_2.removeClass('active');
        links_1.parents().removeClass('selected');
        links_1.css({
            'background-color': '',
            'color': ''
        });

        $(this).parent().parent().addClass('selected');
        $(this).addClass('active');
        console.log($(this)[0].id);
        localStorage.setItem('sublink', $(this)[0].id);
        $(this).parent().prev().css('background-color', '#262626');
    });

});
//=====================================================================