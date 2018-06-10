<%-- 
    Document   : servicecategoryinsert
    Created on : May 1, 2018, 10:40:44 AM
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
        <title>Insert Service Category</title>
        <script type="text/javascript">
            $.subscribe('resetAddButton', function (event, data) {
                $("#iroleType").val("");
                $("#idescription").val("");
                $("#istatus").val("");

                $("#divmsginsert").empty();

            });
        </script>
    </head>
    <body>
        <s:div id="divmsginsert">
            <s:actionerror theme="jquery"/>
            <s:actionmessage theme="jquery"/>
        </s:div>
        <div class = "tb-modal-body tb-header-text" >
            <div class="tb-modal">
                <div class="containe-fluid">
                    <s:form id="servicecategoryinsert" method="post" action="ServiceCategory" theme="simple" cssClass="form">   
                        <div class="row"> 
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Service Category Code</label>
                                    <s:textfield name="roleType" id="iroleType" maxLength="6" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g, ''))" onkeypress="return alpha(event)"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Description</label>
                                    <s:textfield name="description" id="idescription" maxLength="30" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" onkeypress="return alpha(event)"/>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <span style="color: red">*</span><label >Status</label>
                                    <s:select  id="istatus" list="%{statusList}"  name="status" headerKey=""  headerValue="--Select Status--" listKey="statuscode" listValue="description" value="%{status}" disabled="false" cssClass="form-control"/>
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
                                        onClickTopics="resetAddButton"
                                        />                        
                                </div>
                                <div class="form-group">
                                    <s:url action="addServiceCategory" var="inserturl"/>
                                    <sj:submit
                                        button="true"
                                        value="Add"
                                        href="%{inserturl}"
                                        onClickTopics=""
                                        targets="divmsginsert"
                                        id="addbtn"
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