<%-- 
    Document   : servicecategoryedit
    Created on : May 1, 2018, 10:41:04 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Edit Service Category</title>
            <script type="text/javascript">
                function editServiceCategory(keyval) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/findServiceCategory.action',
                        data: {roleType: keyval},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            $('#divmsgupdate').empty();
                            var msg = data.message;
                            if (msg) {
                                //                                                        alert(data.message)
                                $('#e_roleType').attr('readOnly', false);
                                $('#e_roleType').val("");
                                $("#e_roleType").css("color", "black");
                                $('#e_description').val("");
                                $('#e_status').val("");
                                $('#divmsgupdate').text("");

                            } else {
                                $('#oldvalue').val(data.oldvalue);
                                $('#e_roleType').val(data.username);
                                $('#e_roleType').attr('readOnly', true);
                                $("#e_roleType").css("color", "#858585");
                                $('#e_description').val(data.description);
                                $('#e_status').val(data.status);

                            }
                        },
                        error: function (data) {
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                        }
                    });
                }
                function cancelData() {
                    var rtype = $('#e_roleType').val();
                    editServiceCategory(rtype);
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
                    <s:form id="servicecategoryedit" method="post" action="ServiceCategory" theme="simple" cssClass="form" >
                        <div class="row"> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Service Category Code</label>
                                    <s:textfield name="roleType" id="e_roleType"  maxLength="16" readonly="true" cssClass="form-control"  onkeypress="return alpha(event)"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Description</label>
                                    <s:textfield name="description" id="e_description" cssClass="form-control" maxLength="30" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span> <label >Status</label>
                                    <s:select  id="e_status" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" cssClass="form-control"/>
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
                                    <s:url action="updateServiceCategory" var="updateturl"/>
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