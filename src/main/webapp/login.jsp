<%-- 
    Document   : login2
    Created on : Jan 4, 2018, 11:22:01 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Project</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/images/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/bootstrap/css/bootstrap.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/animsition/css/animsition.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/select2/select2.min.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/daterangepicker/daterangepicker.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/css/util.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/css/main.css">


        <!--===============================================================================================-->

        <script>

            window.localStorage.removeItem('link');
            window.localStorage.removeItem('sublink');

            function formSubmit() {
                window.localStorage.removeItem("item");
                $("#formES").submit();
            }
//            var ids = window.localStorage.getItem("item");
//            alert("login "+ ids);
//            $("#" + ids).removeClass("active");


            function encryp() {
                if ($('#password').val() != "") {
                    var ps = $('#password').val();
                    $('#password').val(CryptoJS.MD5(ps));
                    //                    var value = '&lt;%= request.getMethod() %&gt;';
                    //                    alert(CryptoJS.MD5(ps));

                }
            }
            
            function sss(obj){
                $(obj).html("AUTHENTICATING <i style='margin-left:5px;' class='zmdi zmdi zmdi-chart-donut zmdi-hc-1x zmdi-hc-spin'></i>");
            }
            
            

        </script>
    </head>
    <body>

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">
                    <form class="login100-form validate-form" id="formES" novalidate="novalidate" action="CheckUserLogin" method="post">
                        <span class="login100-form-title p-b-26">
                            Top Bass
                        </span>
                        <span class="login100-form-title p-b-48">
                            <!--<i class="zmdi zmdi-font"></i>-->
                            <img src="resources/newassets/Login/Login_v2/images/login/topbaaslogo.png" width="70px" height="auto" style="background: #373536;padding: 4px;border-radius: 13px"/>
                        </span>

                        <div class="wrap-input100 validate-input" data-validate = "Enter Username">
                            <input class="input100" type="text" name="username">
                            <span class="focus-input100" data-placeholder="Username"></span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate="Enter password">
                            <span class="btn-show-pass">
                                <i class="zmdi zmdi-eye"></i>
                            </span>
                            <input class="input100" type="password" name="password">
                            <span class="focus-input100" data-placeholder="Password"></span>
                        </div>
                        
                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn" onclick="sss(this)">
                                    Login
                                    
                                </button>
                            </div>
                        </div>

                        <div class="text-center p-t-115">
                            <span class="txt1" style="color: #f32020">
                                <s:if test="hasActionErrors()">
                                    <div class="error-dis">
                                        <i class="zmdi zmdi-alert-triangle mdc-text-red"></i>
                                            <s:actionerror cssStyle="list-style:none;"/></i> 
                                    </div>
                                </s:if>
                                <s:if test="hasActionMessages()">
                                    <div class="error-dis">
                                        <i class="zmdi zmdi-alert-triangle mdc-text-red"></i>
                                            <s:actionmessage cssStyle="list-style:none"/></i>
                                    </div>  
                                </s:if>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div id="dropDownSelect1"></div>

        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/animsition/js/animsition.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/bootstrap/js/popper.js"></script>
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/daterangepicker/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/daterangepicker/daterangepicker.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/vendor/countdowntime/countdowntime.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resources/newassets/Login/Login_v2/js/main.js"></script>

        
    </body>
</html>
