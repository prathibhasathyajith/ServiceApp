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
                        zIndex: '100000000'
                    },
                    message: '<img height="136" width="136" src="${pageContext.request.contextPath}/resources/images/Ellipsis.svg" />',
                    baseZ: 20000
                });
            });
            $(document).ajaxStop(function () {
                $.unblockUI();
            });

        });

    </script>
</head>
<header>
    <div class="cont"><i class="fa fa-cube" aria-hidden="true"></i> Project</div><span id="sysType">Logged as ${SYSTEMUSER}</span>
    <div class="coner">
        <div class="box"></div>
        <div class="box"></div>
        <div class="box"></div>
    </div>
</header>
<div class="dropdown">
        <a style="float:left" class="logoutBut headerBut" href="logoutLogin.action">Logout</a>
</div>

