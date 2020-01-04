<%-- 
    Document   : header
    Created on : Dec 26, 2017, 8:59:36 AM
    Author     : prathibha_s
--%>
<head>
    <script>
        $(document).ready(function () {
            $(document).ajaxStart(function () {
                $.blockUI({css: {
                        border: 'transparent',
                        backgroundColor: 'transparent',
                        zIndex: '9000000000'
                    },
                    message: '<img height="136" width="136" src="${pageContext.request.contextPath}/resources/newassets/assets/loader/bar.svg" />',
                    baseZ: 9000000000
                });
            });
            $(document).ajaxStop(function () {
                $.unblockUI();
            });

        });

        function goHome() {
            window.location = "${pageContext.request.contextPath}/viewHomedefaultHome.action?";
        }

    </script>
</head>
<div class="tb-header tb-fixed-header">
    <div class="tb-click-home tb-tooltip" onclick="goHome()" >
        <span class="tb-tooltiptext tb-tooltiptext-home">Home</span>
        <div class="tb-header-logo f-left">
            <img src="resources/newassets/Login/Login_v2/images/login/topbaaslogo.png" width="40" height="auto" style="background: transparent;padding: 2px;border-radius: 5px;margin: 2px"/>
            <!--<img src="assets/image/logo2.png" width="40" height="auto" />-->
        </div>
        <div class="change1 tb-header-margin color1 tb-header-text f-left" >
            <span>Service<span>App</span></span>
            <span><span></span></span>
        </div>
        <div class="tb-header-divider tb-header-margin tb-header-text f-left">|</div>
        <div class="change2 tb-header-margin color2 tb-header-text f-left">
            <span>Admin<span>Portal</span></span>
            <span><span></span></span>
        </div>
    </div>
    <div class="tb-header-logout tb-header-margin tb-header-text f-right tb-tooltip" ><span class="tb-tooltiptext tb-tooltiptext-logout">Logout</span><a href="LogoutUserLogin.action?message=error3"><i class="material-icons" id="logout_lock">lock_open</i></a></div>
    <div class="tb-header-passchange tb-header-margin tb-header-text f-right tb-tooltip" ><span class="tb-tooltiptext tb-tooltiptext-cpass">Change Password</span><a href="ViewChangePassword.action?message=error3"><i class="material-icons">fingerprint</i></a></div>
</div>
<!--<i class="material-icons">rotate_left</i>-->