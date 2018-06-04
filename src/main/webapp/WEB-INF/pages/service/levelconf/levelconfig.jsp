<%-- 
    Document   : levelconfig
    Created on : Jun 4, 2018, 2:24:46 PM
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
            function editLevelConfig() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/findLevelConfig',
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        var msg = data.message;
                        if (msg) {
                            alert(data.message);
                        } else {
                            $('#basic').val(data.basic);
                            $('#basicDes').val(data.basicDes);
                            $('#basicPrice').val(data.basicPrice);
                            $('#basicSerCont').val(data.basicSerCont);
                            
                            $('#gold').val(data.gold);
                            $('#goldDes').val(data.goldDes);
                            $('#goldPrice').val(data.goldPrice);
                            $('#goldSerCont').val(data.goldSerCont);
                            
                            $('#silver').val(data.silver);
                            $('#silverDes').val(data.silverDes);
                            $('#silverPrice').val(data.silverPrice);
                            $('#silverSerCont').val(data.silverSerCont);
                            
                            $('#platinum').val(data.platinum);
                            $('#platinumDes').val(data.platinumDes);
                            $('#platinumPrice').val(data.platinumPrice);
                            $('#platinumSerCont').val(data.platinumSerCont);
                            
                            $('#bronze').val(data.bronze);
                            $('#bronzeDes').val(data.bronzeDes);
                            $('#bronzePrice').val(data.bronzePrice);
                            $('#bronzeSerCont').val(data.bronzeSerCont);
                            
                        }
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
//                        $("#deleteerrordialog").html("Error occurred while processing.").dialog('open');
                    }
                });
            }

            function resetAllData() {

                var s = $("#resetButton").is(':disabled')
                if (s == true) {
                    
                    $('#divmsg').text("");
                } else {
                    editLevelConfig();
                }

            }

            function resetFieldData() {
            }
        </script>
        <title></title>
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
            <div class="tb-breadcrumb">Service > Level Configurations</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="levelConfig" method="post" action="LevelConfig" theme="simple" cssClass="form" >
                        <table>
                            <thead>
                                <th>Level</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Service Count</th>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><s:textfield cssClass="form-control" name="basic" id="basic" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="basicDes" id="basicDes" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="basicPrice" id="basicPrice" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" /> </td>
                                    <td><s:textfield cssClass="form-control" name="basicSerCont" id="basicSerCont" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" /> </td>
                                </tr>
                                <tr>
                                    <td><s:textfield cssClass="form-control" name="bronze" id="bronze" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="bronzeDes" id="bronzeDes" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="bronzePrice" id="bronzePrice" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/> </td>
                                    <td><s:textfield cssClass="form-control" name="bronzeSerCont" id="bronzeSerCont" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"/> </td>
                                </tr>
                                <tr>
                                    <td><s:textfield cssClass="form-control" name="gold" id="gold" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="goldDes" id="goldDes" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="goldPrice" id="goldPrice" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/> </td>
                                    <td><s:textfield cssClass="form-control" name="goldSerCont" id="goldSerCont" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"/> </td>
                                </tr>
                                <tr>
                                    <td><s:textfield cssClass="form-control" name="platinum" id="platinum" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="platinumDes" id="platinumDes" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="platinumPrice" id="platinumPrice" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/> </td>
                                    <td><s:textfield cssClass="form-control" name="platinumSerCont" id="platinumSerCont" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"/> </td>
                                </tr>
                                <tr>
                                    <td><s:textfield cssClass="form-control" name="silver" id="silver" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="silverDes" id="silverDes" readonly="true"/> </td>
                                    <td><s:textfield cssClass="form-control" name="silverPrice" id="silverPrice" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))"/> </td>
                                    <td><s:textfield cssClass="form-control" name="silverSerCont" id="silverSerCont" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))"/> </td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group  ">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                        </div>
                        <div class="row"></div>
                        <s:url var="updateurl" action="updateLevelConfig"/>
                        <div class="row form-inline">
                            <!--<div class="col-sm-2"></div>-->
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <sj:submit button="true" 
                                               value="Reset" 
                                               name="reset" 
                                               onClick="resetAllData()"
                                               cssClass="uinew-button-reset"
                                               />
                                </div>
                                <div class="form-group">
                                    <sj:submit button="true" 
                                               href="%{updateurl}" 
                                               value="Update" 
                                               targets="divmsg"   
                                               id="updateButton"
                                               cssClass="uinew-button-submit"
                                               />
                                </div>
                            </div>
                        </div>

                    </s:form>
                </div>
                <div class="tb-table"></div>
            </div>
        </div>
    </body>
</html>
