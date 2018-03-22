<%-- 
    Document   : passwordreset
    Created on : Mar 7, 2014, 12:31:53 PM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="resouces/css/common/common_popup.css"/>
        <%@include file="/stylelinks.jspf" %>
        <title> Password Change </title>
        <script type="text/javascript">
            function resetAllData() {
                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#currpwd').val("");
                $('#divmsg').text("");
                logout(false);
            }

            function resetFieldData() {
                $('#newpwd').val("");
                $('#renewpwd').val("");
                $('#currpwd').val("");
            }

            function logout(param) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/LogOutUserpaschanged.action',
                    data: {},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

//            $(document).ready(function () {
//                $.each($('.tooltip'), function (index, element) {
//                    $(this).remove();
//                });
//                $('[data-toggle="tooltip"]').tooltip({
//                    'placement': 'right',
//                    'container': 'body'
//                });
//            });
        </script>
        <style>
            .tooltip {

                background-color: black;
                color: #fff;

                border-radius: 6px;
                padding: 5px 0;
                white-space: pre-line;
            }
            .tooltip-inner {
                min-width: 100px;
                max-width: 100%; 
                text-align: left;
            }

        </style>
    </head>
    <body style="">
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbarpassreset.jsp"/>
        <!--body content-->
        <div class="tb-body tb-header-text">
            <s:div id="divmsg">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
            <div class="tb-breadcrumb">First time password</div>
            <div class="tb-form">
                <div class="containe-fluid">
                <s:form action="PasswordReset" theme="simple" method="post" id="pwdResetform" cssClass="form">
                    <s:hidden name="husername" id="husername" />
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>Username </label>
                                <s:textfield cssClass="form-control" name="username" id="username" disabled="true"/>
                            </div>
                        </div>
                    </div>
                    <div class="row"></div>

                    <div class="row">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>Current Password </label>
                                <s:password cssClass="form-control" name="currpwd" id="currpwd"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>New Password </label>
                                <s:password cssClass="form-control" name="newpwd" id="newpwd" data-toggle="tooltip" data-html="true" title="%{pwtooltip}"/>

                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>Retype New Password </label>
                                <s:password cssClass="form-control" name="renewpwd" id="renewpwd"/>
                            </div>
                        </div>
                    </div>
                    <div class="row form-inline">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <s:url var="paschng" action="Updatepaschanged"/>  
                                <sj:submit 
                                    button="true"
                                    href="%{paschng}"
                                    value="Accept"
                                    targets="divmsg"
                                    cssClass="uinew-button-submit"
                                    />
                            </div> 
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    onClick="resetAllData()"                                                   
                                    cssClass="uinew-button-reset"
                                    />
                            </div>
                        </div>
                    </div>
                </s:form>
            </div>
        </div>


        <%--<jsp:include page="/footer.jsp"/>--%>

    </body>
</html>



