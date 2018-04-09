<%-- 
    Document   : smstemplateedit
    Created on : Mar 26, 2018, 4:43:32 PM
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
        <title>Update SMS Template</title>
        <script>

            function editSmsTemplate(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindSMSTemplate.action',
                    data: {messageId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#e_messageid').val("");
                            $('#e_transactiontype').val("");
                            $('#e_message').val("");

//                            $('#updateButtonedit').button("disable");
                            $('#divmsgupdateupdate').text("");
                        } else {
                            $('#e_messageid').attr('readOnly', true);

                            $('#e_transactiontype').attr('readOnly', true);
                            $('#e_message').val(data.description);



                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {

                var e_messageid = $('#e_messageid').val();
                editSmsTemplate(e_messageid);
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
                    <s:form id="smsedit" method="post" action="SMSTemplate"  theme="simple" cssClass="form"> 
                        <s:hidden name="oldvalue" id="oldvalue"/>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Message ID</label>
                                    <s:textfield cssClass="form-control" name="messageId" id="e_messageid" maxLength="20"  readonly="true" />
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Transaction Type</label>
                                    <s:select cssClass="form-control" name="txnType" maxLength="3" id="e_transactiontype" headerValue="-- Select --" list="%{txnTypeList}"   headerKey="" listKey="typecode" listValue="description" value="%{txnType}" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Message</label>
                                    <s:textarea style="margin-bottom: 0px; padding-top: 7px; border-radius: 4px !important;font-size:12px" cssClass="form-control" id="e_message" name="description" rows="10" cols="115" maxlength="1024" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row ">
                        <div class="horizontal_line_popup"></div>
                    </div>
                    <div class="row form-inline">
                        <div class="col-sm-8">
                            <div class="form-group">
                                <span class="mandatoryfield">Mandatory fields are marked with *</span>
                            </div>
                        </div>
                        <div class="col-sm-12 text-right">
                            <div class="form-group" >
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    name="reset" 
                                    cssClass="uinew-button-reset"
                                    onClick="cancelData()"
                                    />                        
                            </div>
                            <div class="form-group" >

                                <s:url action="updateSMSTemplate" var="updateturl"/>
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
