<%-- 
    Document   : login2
    Created on : Jan 4, 2018, 11:22:01 AM
    Author     : prathibha_s
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <title>Local Government Election Campaign Review Login</title>
        <link href="${pageContext.request.contextPath}/resources/assets/Css/login.css" rel="stylesheet" />
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

        </script>
    </head>

    <body>
        <div class="es-mainContent">
            <div class="es-mainbox">
                <div class="es-textContent es-text">
                    <span>Project</span>
                    <span></span>
                    <span>Login</span>
                </div>
                <form id="formES" novalidate="novalidate" action="CheckUserLogin" method="post" >
                    <div class="es-fieldContent">
                        <label class="es-label es-text2">Username(Admin/Party)</label>
                        <input type="text" class="es-text es-field" name="username" >
                        <label class="es-label es-text2">Password</label>
                        <input type="password" class="es-text es-field"  name="password">
                    </div>
                </form>
                <div class="es-buttonContent es-text" onclick="formSubmit()"></div>
                <div class="es-messageContent es-text">
                    <span>
                        <s:if test="hasActionErrors()">
                            <div class="error-dis">
                                <i class="fa fa-remove-sign" style="color: #ff2222;">
                                    <s:actionerror cssStyle="list-style:none;"/></i> 
                            </div>
                        </s:if>
                        <s:if test="hasActionMessages()">
                            <div class="error-dis">
                                <i class="fa fa-remove-sign" style="color: green;">
                                    <s:actionmessage cssStyle="list-style:none"/></i>
                            </div>  
                        </s:if>
                    </span>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/resources/assets/Js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/Js/main.js"></script>
    </body>

</html>
