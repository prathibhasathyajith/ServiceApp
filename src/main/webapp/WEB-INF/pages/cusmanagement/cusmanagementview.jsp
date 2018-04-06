<%-- 
    Document   : cusmanagementview
    Created on : Apr 4, 2018, 11:17:25 AM
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

//                $(document).ready(function () {
//                    $("#own_img").on('click', function () {
//                        $("#ownImage").click();
//                    });
//                    $("#police_report").on('click', function () {
//                        $("#prImage").click();
//                    });
//                    $("#birth_cert").on('click', function () {
//                        $("#bcImage").click();
//                    });
//                    $("#qualifiy_img").on('click', function () {
//                        $("#qlImage").click();
//                    });
//                });

//                function changeOwnImageEdit() {
//                    $("#ownImage").change(function (event) {
//                        var tmppath = URL.createObjectURL(event.target.files[0]);
//                        $("#own_edit").attr("src", tmppath);
//                    });
//                }
//
//                function changePrImageEdit() {
//                    $("#prImage").change(function (event) {
//                        var tmppath = URL.createObjectURL(event.target.files[0]);
//                        $("#pr_edit").attr("src", tmppath);
//                    });
//                }
//
//                function changeBcImageEdit() {
//                    $("#bcImage").change(function (event) {
//                        var tmppath = URL.createObjectURL(event.target.files[0]);
//                        $("#bc_edit").attr("src", tmppath);
//                    });
//                }
//
//                function changeQlImageEdit() {
//                    $("#qlImage").change(function (event) {
//                        var tmppath = URL.createObjectURL(event.target.files[0]);
//                        $("#ql_edit").attr("src", tmppath);
//                    });
//                }

                function imagePreview2() {

                    var ownImg = '<s:property value="editOwnImg"/>';
                    var prImg = '<s:property value="editPrImg"/>';
                    var bcImg = '<s:property value="editBcImg"/>';
                    var qlImg = '<s:property value="editQlImg"/>';

                    if (ownImg == '') {
                        $("#own_edit2").attr("src", "${pageContext.request.contextPath}/resources/images/male.png");
                        $("#own_edit2").attr({"width": "100", "height": "auto"});
                    }
                    if (prImg == '') {
                        $("#pr_edit2").attr("src", "${pageContext.request.contextPath}/resources/images/no-image.jpg");
                        $("#pr_edit2").attr({"width": "100", "height": "100"});
                    }
                    if (bcImg == '') {
                        $("#bc_edit2").attr("src", "${pageContext.request.contextPath}/resources/images/no-image.jpg");
                        $("#bc_edit2").attr({"width": "100", "height": "100"});
                    }
                    if (qlImg == '') {
                        $("#ql_edit2").attr("src", "${pageContext.request.contextPath}/resources/images/no-image.jpg");
                        $("#ql_edit2").attr({"width": "100", "height": "100"});
                    }
                }

                // image set
                imagePreview2();


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
                
                function sdd(){
                    var name = $("#firstName").val();
                    $("#usn").attr('download',name+'.jpg');
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
                                <div class="circle upload-button" >  
                                    <a href="data:image/jpeg;base64,<s:property value="editOwnImg"/>" download="user_image.jpg" id="usn" onclick="sdd()">
                                        <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editOwnImg"/>" id="own_edit2" name="editOwnImg">
                                    </a>
                                </div>
                                <div class="p-image">
                                    <label>User Image</label>
                                    <!s:file name="ownImage" id="ownImage" onclick="changeOwnImageEdit()" cssClass="file-upload"  /> 
                                </div>
                            </div>
                        </div>
                        <div class="row row_popup">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="">
                                    <label>User Id</label>
                                    <s:textfield  name="userId" id="userId" maxLength="11" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>First Name</label>
                                    <s:textfield  name="firstName" id="firstName" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Last Name</label>
                                    <s:textfield  name="lastName" id="lastName" maxLength="128" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Email</label>
                                    <s:textfield  name="email" id="email" maxLength="256" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9@.]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9@.]/g,''))" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>NIC</label>
                                    <s:textfield  name="nic" id="nic" maxLength="12" onkeyup="$(this).val($(this).val().replace(/[^0-9Vv]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9Vv]/g,''))" cssClass="form-control" readonly="true" />
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="">
                                    <label>Address</label>
                                    <s:textfield  name="address" id="address" maxLength="1024" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Area</label>
                                    <s:textfield  name="area" id="area" maxLength="64" cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>District</label>
                                    <s:textfield  name="district" id="district" maxLength="256"  cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:textfield  name="status" id="status" maxLength="256"  cssClass="form-control" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row row_popup">
                            <div class="horizontal_line_popup"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-offset-1 col-sm-3">
                                <div class="squar upload-button" id=""> 
                                    <a href="data:image/jpeg;base64,<s:property value="editPrImg"/>" download="police_report.jpg">
                                        <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editPrImg"/>" id="pr_edit2" name="editPrImg" >
                                    </a>
                                </div>
                                <div class="p-image" style="text-align: left;margin: 0 15px;">
                                    <label>Police Report</label>
                                    <!s:file name="prImage" id="prImage" onclick="changePrImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
                            <div class="col-sm-offset-1 col-sm-3">
                                <div class="squar upload-button" id="">  
                                    <a href="data:image/jpeg;base64,<s:property value="editBcImg"/>" download="birth_certificate.jpg">
                                        <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editBcImg"/>" id="bc_edit2" name="editBcImg">
                                    </a>
                                </div>
                                <div class="p-image" style="text-align: left;margin: 0 15px;">
                                    <label>Birth Certificate</label>
                                    <!s:file name="bcImage" id="bcImage" onclick="changeBcImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
                            <div class="col-sm-offset-1 col-sm-3">
                                <div class="squar upload-button" id=""> 
                                    <a href="data:image/jpeg;base64,<s:property value="editQlImg"/>" download="qualification_details.jpg">
                                        <img class="profile-pic " src="data:image/jpeg;base64,<s:property value="editQlImg"/>" id="ql_edit2" name="editQlImg">
                                    </a>
                                </div>
                                <div class="p-image" style="text-align: left;margin: 0 0px;">
                                    <label>Qualification Details</label>
                                    <!s:file name="qlImage" id="qlImage" onclick="changeQlImageEdit()" cssClass="file-upload" accept="image/*" /> 
                                </div>
                            </div>
                        </div>
                    </s:form>


                    <div id="billCopyDialog" hidden="true" class="text-center">   
                        <div class="">
                            <img width="100%" class="profile-pic " src="data:image/jpeg;base64,<s:property value="editOwnImg"/>" id="own_edit3" name="editOwnImg">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
