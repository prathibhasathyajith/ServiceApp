<%-- 
    Document   : cusmanagement
    Created on : Apr 4, 2018, 11:17:07 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@include file="/stylelinks.jspf" %>
        <script type="text/javascript">
            function changeOwnImageEdit() {
                $("#ownImageEdit").change(function (event) {
                    var tmppath = URL.createObjectURL(event.target.files[0]);
                    $("#own_edit").attr("src", tmppath);
                });
            }
            function editCusMgt(){
                $.ajax({
                    url: '${pageContext.request.contextPath}/findCustomerMgt.action',
                    data: {userId: 32},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                            $("#own_edit").attr("src", "data:image/jpeg;base64," + data.editOwnImg);
                        
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
        <!--header-->
        <jsp:include page="/header.jsp"/>
        <!--nav bar-->
        <jsp:include page="/navbar.jsp"/>
        <!--body content-->
        <div class="tb-body">
            <s:div id="divmsg">
                <s:actionerror theme="jquery"/>
                <s:actionmessage theme="jquery"/>
            </s:div>
            <div class="tb-breadcrumb">Customer Mgt > Customer Management</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="cusmgt" method="post" action="CustomerMgt" theme="simple" cssClass="form" enctype="multipart/form-data">
                        <div class="row row_popup">
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Logo</label>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <img class="image_card" src="data:image/jpeg;base64,<s:property value="editOwnImg"/>" id="own_edit" name="editOwnImg">
                                        </div>                               
                                        <div class="col-sm-5" style="margin-top: 40px;">
                                            <s:file name="ownImage" id="ownImageEdit" onclick="changeOwnImageEdit()"/>                               
                                        </div>
                                    </div>
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
                                <div class="form-group" style=" margin-left: 10px;margin-right: 0px;">
                                    <sj:submit 
                                        button="true" 
                                        value="Reset" 
                                        onClick="editCusMgt()"
                                        cssClass="btn btn-default btn-sm"
                                        />                          
                                </div>
                                <div class="form-group" style=" margin-left: 0px;margin-right: 10px;">
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
            <div class="tb-table">

            </div>
        </div>
    </body>
</html>
s
