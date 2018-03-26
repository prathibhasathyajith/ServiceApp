<%-- 
    Document   : emailtemplateedit
    Created on : Mar 26, 2018, 4:43:11 PM
    Author     : prathibha_s
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" href="resouces/css/common/common_popup.css">-->
        <title>Update Email Template</title>
        <script>

            function editEmaiTemplate(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindEmailTemplate.action',
                    data: {messageid: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#e_messageid').val("");
                            $('#e_transactiontype').val("");
                            $('#e_subject').val("");
                            $('#e_messageEmail').val("");


//                            $('#updateButtonedit').button("disable");
                            $('#divmsgupdateupdate').text("");
                        } else {
                            $('#e_messageid').attr('readOnly', true);

                            $('#e_transactiontype').val(data.transactiontype);
                            $('#e_subject').val(data.subject);

                            $('#e_messageEmail').val(data.messageEmail);

                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {

                var e_messageid = $('#e_messageid').val();
                editEmaiTemplate(e_messageid);
            }

        </script>

    </head>
    <body>
        <s:div id="divmsgupdate">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class="tb-modal-body tb-header-text">
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="emailedit" method="post" action="EmailTemplate"  theme="simple" cssClass="form"> 
                        <s:hidden name="oldvalue" id="oldvalue"/>
                        <div class="row">
                            <div class="col-sm-12">
                                <table>
                                    <tbody>
                                        <tr>
                                            <td>Message ID <span style="color: red">*</span></td>
                                            <td><s:textfield cssClass="form-control-disable" name="messageid" id="e_messageid" maxLength="20"  readonly="true" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><br></td><td></td><td></td>
                                        </tr>
                                        <tr>   
                                            <td>Transaction Type <span style="color: red">*</span></td>

                                            <td>   <s:select cssClass="form-control-disable" name="transactiontype" maxLength="3" id="e_transactiontype" headerValue="-- Select --" list="%{transactiontypeList}"   headerKey="" listKey="typecode" listValue="description" value="%{transactiontype}" disabled="true"/>
                                            </td> 
                                        </tr> 
                                        <tr>
                                            <td><br></td><td></td><td></td>
                                        </tr>
                                        <tr><td><s:hidden name="transactiontype" id="e_transactiontype"/></td></tr>
                                        <tr>
                                            <td>Subject <span style="color: red">*</span></td>
                                            <td><s:textfield cssClass="form-control"  name="subject" id="e_subject" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><br></td><td></td><td></td>
                                        </tr>
                                        <tr>
                                            <td>Message <span style="color: red">*</span></td>

                                            <td><s:textarea style="margin-bottom: 0px; padding-top: 7px; border-radius: 4px;" cssClass="form-control" id="e_messageEmail" name="messageEmail" rows="10" cols="115" maxlength="1024" />


                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="horizontal_line_popup"></div>
                        </div>

                        <div class="row form-inline">
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                            <div class="col-sm-12 text-right">
                                <div class="form-group">
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        name="reset" 
                                        cssClass="uinew-button-reset"
                                        onClick="cancelData()"
                                        />                        
                                </div>
                                <div class="form-group">

                                    <s:url action="updateEmailTemplate" var="updateturl"/>
                                    <sj:submit
                                        button="true"
                                        value="Update"
                                        href="%{updateturl}"
                                        targets="divmsgupdate"
                                        id="updatebtn"
                                        cssClass="uinew-button-submit" 
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
