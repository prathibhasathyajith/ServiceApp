<%-- 
    Document   : welcomepage
    Created on : Jun 6, 2014, 1:45:39 PM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <title>Project</title>
        <%@include file="/stylelinks.jspf" %>


    </head>
    <script type="text/javascript">

        function myFunction() {
//            window.open("${pageContext.request.contextPath}/login.jsp", "MsgWindow", "width=1350, height=660,scrollbars=1,resizable=yes");
            window.open("${pageContext.request.contextPath}/login.jsp");
        }

    </script>

    <style>

        @font-face {
            font-family: "allerB1";
            src: url("resources/font/AllerDisplay.woff") format('woff');
        }
        @font-face {
            font-family: "allerB2";
            src: url("resources/font/Aller_Bd.woff") format('woff');
        }
        #box{
            margin-left: 50%;
            margin-top: 18%;
            position: absolute;
            padding: 14px;
            width: 220px;
            border-radius: 29px;
            height: 142px;
            background-color: white;
            transition: 0.5s;

        }


        #boxx:hover{
            cursor: pointer;

        }

        #boxx:hover ~ #box{
            box-shadow: 0px 0px 20px gray;

        }
        #es{
            font-family: 'allerB1';
            font-size: 30px;
            text-align: center;
            color:white;
            /*position: absolute;*/
        }




    </style>
    <body style="background-color: #a32588">

        <div class="container">
            <div class="container">
                <div class="col-md-12" style="margin-top: 6%"></div>
                <div class="col-md-12"><div id="es">Project</div></div>

                <div class="col-md-12" id="boxx">
                    <img style="margin-left: 25%" src="resources/assets/Images/election-dy.jpg" width="400" height="auto" onclick="myFunction()"/>
                </div>
                <div class="col-md-3"></div>

            </div>
        </div>

    </body>

</html>
