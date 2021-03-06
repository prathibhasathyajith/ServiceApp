<%-- 
    Document   : cusmanagementedit
    Created on : Apr 4, 2018, 11:17:18 AM
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
            <title>Update Customer</title>

            <script type="text/javascript">

                $(document).ready(function () {
                    $("#own_img").on('click', function () {
                        $("#ownImage").click();
                    });
                    $("#police_report").on('click', function () {
                        $("#prImage").click();
                    });
                    $("#birth_cert").on('click', function () {
                        $("#bcImage").click();
                    });
                    $("#qualifiy_img").on('click', function () {
                        $("#qlImage").click();
                    });
                });

                function changeOwnImageEdit() {
                    $("#ownImage").change(function (event) {
                        var tmppath = URL.createObjectURL(event.target.files[0]);
                        $("#own_edit").attr("src", tmppath);
                    });
                }

                function changePrImageEdit() {
                    $("#prImage").change(function (event) {
                        var tmppath = URL.createObjectURL(event.target.files[0]);
                        $("#pr_edit").attr("src", tmppath);
                    });
                }

                function changeBcImageEdit() {
                    $("#bcImage").change(function (event) {
                        var tmppath = URL.createObjectURL(event.target.files[0]);
                        $("#bc_edit").attr("src", tmppath);
                    });
                }

                function changeQlImageEdit() {
                    $("#qlImage").change(function (event) {
                        var tmppath = URL.createObjectURL(event.target.files[0]);
                        $("#ql_edit").attr("src", tmppath);
                    });
                }

                function imagePreview() {

                    var ownImg = '<s:property value="editOwnImg"/>';
                    var prImg = '<s:property value="editPrImg"/>';
                    var bcImg = '<s:property value="editBcImg"/>';
                    var qlImg = '<s:property value="editQlImg"/>';

                    if (ownImg == '') {
                        $("#own_edit").attr("src", "${pageContext.request.contextPath}/resources/images/male.png");
                        $("#own_edit").attr({"width": "100", "height": "auto"});
                    }
                    if (prImg == '') {
                        $("#pr_edit").attr("src", "${pageContext.request.contextPath}/resources/images/no-image.jpg");
                        $("#pr_edit").attr({"width": "100", "height": "100"});
                    }
                    if (bcImg == '') {
                        $("#bc_edit").attr("src", "${pageContext.request.contextPath}/resources/images/no-image.jpg");
                        $("#bc_edit").attr({"width": "100", "height": "100"});
                    }
                    if (qlImg == '') {
                        $("#ql_edit").attr("src", "${pageContext.request.contextPath}/resources/images/no-image.jpg");
                        $("#ql_edit").attr({"width": "100", "height": "100"});
                    }
                }

                // image set
                imagePreview();

                function resetFieldData() {
                    $('#updatedialog').animate({ scrollTop: 0 }, 'fast');
                    $("#gridtable").jqGrid('setGridParam', {page: 1});
                    jQuery("#gridtable").trigger("reloadGrid");
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
                    <s:form id="cusmgt" method="post" action="CustomerMgt" theme="simple" cssClass="form" enctype="multipart/form-data">
                        <div class="row" >
                            <div class="col-sm-offset-5 col-sm-2">
                                <div class="circle upload-button" id="own_img">  
                                    <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editOwnImg"/>" id="own_edit" name="editOwnImg">
                                </div>
                                <div class="p-image">
                                    <span style="color: red">*</span><label>User Image</label>
                                    <s:file name="ownImage" id="ownImage" onclick="changeOwnImageEdit()" cssClass="file-upload"  /> 
                                </div>
                            </div>
                        </div>
                        <div class="row row_popup">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>User Id</label>
                                    <s:textfield  name="userId" id="userId" maxLength="11" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>First Name</label>
                                    <s:textfield  name="firstName" id="firstName" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Last Name</label>
                                    <s:textfield  name="lastName" id="lastName" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Email</label>
                                    <s:textfield  name="email" id="email" maxLength="256" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9@.]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9@.]/g,''))" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>NIC</label>
                                    <s:textfield  name="nic" id="nic" maxLength="12" onkeyup="$(this).val($(this).val().replace(/[^0-9Vv]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9Vv]/g,''))" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Address</label>
                                    <s:textfield  name="address" id="address" maxLength="1024" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Area</label>
                                    <s:textfield  name="area" id="area" maxLength="64" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>District</label>
                                    <s:textfield  name="district" id="district" maxLength="256"  cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Status</label>
                                    <s:select  id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label>Qualification</label>
                                    <s:select  id="qualify" list="%{qualifyList}"  headerValue="--Select Qualification--" headerKey="" name="qualify" listKey="id" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-offset-1 col-sm-3">
                                <div class="squar upload-button" id="police_report">  
                                    <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editPrImg"/>" id="pr_edit" name="editPrImg">
                                </div>
                                <div class="p-image" style="text-align: left;margin: 0 15px;">
                                    <span style="color: red">*</span><label>Police Report</label>
                                    <s:file name="prImage" id="prImage" onclick="changePrImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
                            <div class="col-sm-offset-1 col-sm-3">
                                <div class="squar upload-button" id="birth_cert">  
                                    <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editBcImg"/>" id="bc_edit" name="editBcImg">
                                </div>
                                <div class="p-image" style="text-align: left;margin: 0 15px;">
                                    <span style="color: red">*</span><label>Birth Certificate</label>
                                    <s:file name="bcImage" id="bcImage" onclick="changeBcImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
                            <div class="col-sm-offset-1 col-sm-3">
                                <div class="squar upload-button" id="qualifiy_img">  
                                    <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editQlImg"/>" id="ql_edit" name="editQlImg">
                                </div>
                                <div class="p-image" style="text-align: left;margin: 0 0px;">
                                    <span style="color: red">*</span><label>Qualification Details</label>
                                    <s:file name="qlImage" id="qlImage" onclick="changeQlImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
                        </div>
                        <div class="row row_popup">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <div class="row row_popup form-inline">
                            <div class="col-sm-9">
                                <div class="form-group">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                            <div class="col-sm-3 text-right">
                                <div class="form-group" >
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        onClick="editCusMgt()"
                                        cssClass="uinew-button-reset"
                                        />                          
                                </div>
                                <div class="form-group" >
                                    <s:url action="updateCustomerMgt" var="updateturl"/>
                                    <sj:submit
                                        button="true"
                                        value="Update"
                                        href="%{updateturl}"
                                        targets="divmsgupdate"
                                        id="updateButton"
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
