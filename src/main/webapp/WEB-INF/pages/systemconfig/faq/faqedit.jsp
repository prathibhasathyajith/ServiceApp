<%-- 
    Document   : faqedit
    Created on : Mar 26, 2018, 11:24:13 AM
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
        <title>Update FAQ</title>
        <script>

            function editFAQ(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findFAQ.action',
                    data: {id: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsgupdate').empty();
                        var msg = data.message;
                        if (msg) {
                            $('#e_id').attr('readOnly', false);
                            $('#e_id').val("");
                            $("#e_id").css("color", "black");
                            $('#e_type').val("");
                            $('#e_section').val("");
                            $('#e_question').val("");
                            $('#e_answer').val("");
                            $("#e_status").val("");
                            $('#divmsgupdate').text("");

                        } else {
                            $('#e_id').val(data.id);
                            $('#e_id').attr('readOnly', true);
                            $('#e_type').val(data.type);
                            $("#e_section").val(data.section);
                            $("#e_question").val(data.question);
                            $('#e_answer').val(data.answer);
                            $('#e_status').val(data.status);
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function cancelData() {
                var id = $('#e_id').val();
                editFAQ(id);
            }

            function isNumber(evt) {
                evt = (evt) ? evt : window.event;
                var charCode = (evt.which) ? evt.which : evt.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }

            function alpha(e) {
                var k;
                document.all ? k = e.keyCode : k = e.which;
                return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57) || (k == 13));
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
                    <s:form id="faqedit" method="post" action="FAQ"  theme="simple" cssClass="form"> 
                        <s:hidden name="oldvalue" id="oldvalue"/>

                        <div class="row"> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >ID</label>
                                    <s:textfield name="id" id="e_id"  maxLength="6" readonly="true" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onkeypress="return alpha(event)"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Type</label>
                                    <s:select  id="e_type" list="%{typeList}"  name="type" headerKey=""  headerValue="--Select Type--" listKey="code" listValue="description"  value="%{type}" disabled="false" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Section</label>
                                    <s:textfield name="section" id="e_section" cssClass="form-control" maxLength="20" rows="1" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/>
                                </div>
                            </div>
                        </div>

                        <div class="row"> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Status</label>
                                    <s:select  id="e_status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description"  value="%{status}" disabled="false" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Question</label>
                                    <s:textarea name="question" id="e_question" cssClass="form-control" maxLength="1000" rows="2"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Answer</label>
                                    <s:textarea name="answer" id="e_answer" cssClass="form-control" maxLength="1000" rows="2" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="horizontal_line_popup"></div>
                        </div>

                        <div class="row form-inline">
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                            <div class="col-sm-4">
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
                                    <s:url action="updateFAQ" var="updateturl"/>
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
