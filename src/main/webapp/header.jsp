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

    </script>
</head>
<div class="tb-header tb-fixed-header">
    <div class="tb-header-logo">
        <!--            <img src="assets/image/logo2.png" width="40" height="auto" />-->
    </div>
    <div class="change1 tb-header-margin color1 tb-header-text f-left">
        <span>Service<span>App</span></span>
    </div>
    <div class="tb-header-divider tb-header-margin tb-header-text f-left">|</div>
    <div class="change2 tb-header-margin color2 tb-header-text f-left">
        <span>Admin<span>Portal</span></span>
    </div>
    <div class="tb-header-logout tb-header-margin tb-header-text f-right" title="Logout"><a href="LogoutUserLogin.action?message=error3"><i class="material-icons">lock</i></a></div>
    <div class="tb-header-passchange tb-header-margin tb-header-text f-right" title="Change Password"><a href="ViewChangePassword.action?message=error3"><i class="material-icons">vpn_key</i></a></div>
</div>