<#macro page title>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <title>Chipo's News</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Tooplate">
        <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" rel="stylesheet">
        <link href="/static/css/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="/static/css/fontawesome.css">
        <link rel="stylesheet" href="/static/css/tooplate-main.css">
        <link rel="stylesheet" href="/static/css/owl.css">
    </head>
    <body>

    <#include "header.ftl">

    <#nested/>

    </body>
    </html>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.bundle.min.js"></script>


    <!-- Additional Scripts -->
    <script src="/static/js/owl.js"></script>
    <script src="/static/js/accordations.js"></script>
    <script src="/static/js/main.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            // navigation click actions
            $('.scroll-link').on('click', function (event) {
                event.preventDefault();
                var sectionID = $(this).attr("data-id");
                scrollToID('#' + sectionID, 750);
            });
            // scroll to top action
            $('.scroll-top').on('click', function (event) {
                event.preventDefault();
                $('html, body').animate({scrollTop: 0}, 'slow');
            });
            // mobile nav toggle
            $('#nav-toggle').on('click', function (event) {
                event.preventDefault();
                $('#main-nav').toggleClass("open");
            });
        });

        // scroll function
        function scrollToID(id, speed) {
            var offSet = 0;
            var targetOffset = $(id).offset().top - offSet;
            var mainNav = $('#main-nav');
            $('html,body').animate({scrollTop: targetOffset}, speed);
            if (mainNav.hasClass("open")) {
                mainNav.css("height", "1px").removeClass("in").addClass("collapse");
                mainNav.removeClass("open");
            }
        }

        if (typeof console === "undefined") {
            console = {
                log: function () {
                }
            };
        }
    </script>
</#macro>