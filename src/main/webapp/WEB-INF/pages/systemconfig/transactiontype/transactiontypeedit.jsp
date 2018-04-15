<%-- 
    Document   : transactiontypeedit
    Created on : Mar 26, 2018, 4:44:07 PM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resouces/css/common/common_popup.css">
        <title>Update Transaction Type</title>

        <script>
            function editTransactionType(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindTransactionType.action',
                    data: {transactiontypecode: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#transactiontypecodeedit').val("");
                            $('#transactiontypecodeedit').attr('readOnly', true);
                            $("#transactiontypecodeedit").css("color", "black");
                            $('#descriptionedit').val("");
                            $('#description_receiveredit').val("");
                            $('#sortkeyedit').val("");
                            $('#statusedit').val("");
                            $('#OTPReqStatusedit').val("");
                            $('#history_visibilityedit').val("");
                            $('#OTPReqStatusedit').val("");

                            $('#divmsgupdate').text("");
                            $('#updateButtonedit').button("disable");
                        } else {
                            $('#oldvalue').val(data.oldvalue);
                            $('#transactiontypecodeedit').val(data.transactiontypecode);
                            $('#transactiontypecodeedit').attr('readOnly', true);
                            $("#transactiontypecodeedit").css("color", "#858585");
                            $('#descriptionedit').val(data.description);
                            $('#description_receiveredit').val(data.description_receiver);
                            $('#statusedit').prop('disabled', false);
                            $('#statusedit').val(data.status);
                            $('#OTPReqStatusedit').val(data.OTPReqStatus);
                            $('#history_visibilityedit').val(data.history_visibility);
                            $('#divmsgupdate').text("");
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {
                var transactiontypecode = $('#transactiontypecodeedit').val();
                editTransactionType(transactiontypecode);
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
                    <s:form id="transactiontypeedit" method="post" action="TransactionType" theme="simple" cssClass="form" >
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Transaction Type Code</label>
                                    <s:textfield name="transactiontypecode" id="transactiontypecodeedit" maxLength="2" readonly="true" onkeyup="$(this).val($(this).val().replace(/[^0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Description</label>
                                    <s:textfield  name="description" id="descriptionedit" maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Status</label>
                                    <s:select  id="statusedit" list="%{statusList}"  name="status" headerValue="--Select Status--" headerKey="" listKey="statuscode" listValue="description" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Description Receiver</label>
                                    <s:textfield id="description_receiveredit"  name="description_receiver"  maxLength="64" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                                </div>
                            </div>

                        </div>       
                        <div class="row ">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <div class="row  form-inline">               
                            <div class="col-sm-9">
                                <div class="form-group">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                            <div class="col-sm-3 form-inline">
                                <div class="form-group" >
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        onClick="cancelData()"
                                        cssClass="uinew-button-reset"
                                        />                          
                                </div>
                                <div class="form-group">
                                    <s:url action="UpdateTransactionType" var="updateturl"/>
                                    <sj:submit
                                        button="true"
                                        value="Update"
                                        href="%{updateturl}"
                                        targets="divmsgupdate"
                                        id="updateButtonedit"
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
