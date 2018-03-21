<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<!--<html xmlns="http://www.w3.org/1999/xhtml">-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <%@include file="/stylelinks.jspf" %>
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
                    url: '${pageContext.request.contextPath}/LogOutUserChangePassword.action',
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
        </script>
    </head>

    <body style="">
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="tb-body tb-header-text">
            <s:div id="divmsg">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
            <div class="tb-breadcrumb">Change Password</div>
            <div class="tb-form">
                <s:form  action="PasswordReset" theme="simple" method="post" id="pwdResetform" cssClass="form-inline">
                    <s:hidden name="husername" id="husername" />
                    <div class="containe-fluid">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label >Username </label>
                                    <s:textfield cssClass="form-control" name="username" id="username" disabled="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Current Password </label>
                                    <s:password cssClass="form-control" name="currpwd" id="currpwd"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>New Password </label>
                                    <s:password cssClass="form-control a" name="newpwd" id="newpwd" cssStyle="form-control" data-toggle="tooltip"  data-html="true" title="%{pwtooltip}" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Retype New Password </label>
                                    <s:password cssClass="form-control" name="renewpwd" id="renewpwd" />
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-9">
                                <div class="form-group">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <s:url var="updateurl" action="Updatepaschanged"/>
                                    <sj:submit 
                                        button="true"
                                        href="%{updateurl}"
                                        value="Accept"
                                        targets="divmsg"
                                        id="sdsd"
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
        </div>
    </body>
</html>


