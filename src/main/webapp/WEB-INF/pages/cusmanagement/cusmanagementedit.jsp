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
//                    var readURL = function (input) {
//                        if (input.files && input.files[0]) {
//                            var reader = new FileReader();
//
//                            reader.onload = function (e) {
//                                $('.profile-pic').attr('src', e.target.result);
//                            }
//                            reader.readAsDataURL(input.files[0]);
//                        }
//                    }
//
//                    $(".file-upload").on('change', function () {
//                        readURL(this);
//                    });

                    $(".upload-button").on('click', function () {
                        $(".file-upload").click();
                    });
                });

                function changeOwnImageEdit() {
                    $("#ownImageEdit").change(function (event) {
                        var tmppath = URL.createObjectURL(event.target.files[0]);
                        $("#own_edit").attr("src", tmppath);
                    });
                }
                function editCusMgt() {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/findCustomerMgt.action',
                        data: {userId: 32},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {

                            if (data.editOwnImg != "") {
                                $("#own_edit").attr("src", "data:image/jpeg;base64," + data.editOwnImg);
                            } else {
                                $("#own_edit").attr("src", "${pageContext.request.contextPath}/resources/images/noimage.png");
                                $("#own_edit").attr({"width": "100", "height": "auto"});
                            }

                        },
                        error: function (data) {
                            window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                        }
                    });
                }

                function resetFieldData() {}

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
                            <!--                            <div class="col-sm-8">
                                                            <div class="form-group">
                                                                <span style="color: red">*</span><label >User Image</label>
                                                                <div class="row">
                                                                    <div class="col-sm-3">
                                                                        <img class="image_card" src="data:image/jpeg;base64,<!s:property value="editOwnImg"/>" id="own_edit" name="editOwnImg">
                                                                </div>                               
                                                                <div class="col-sm-5" style="margin-top: 40px;">
                                                                    <!s:file name="ownImage" id="ownImageEdit" onclick="changeOwnImageEdit()"/>                               
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>            -->

                            <div class="col-sm-offset-5 col-sm-2">
                                <div class="circle upload-button">
                                    
                                    <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editOwnImg"/>" id="own_edit" name="editOwnImg">
                                </div>
                                <div class="p-image">
<!--                                    <label>selfie</label>-->
                                    <s:file name="ownImage" id="ownImageEdit" onclick="changeOwnImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
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
                                    <!s:select  id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
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
                                        targets="divmsg"
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
