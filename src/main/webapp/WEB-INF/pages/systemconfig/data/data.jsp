<%-- 
    Document   : data
    Created on : Apr 9, 2018, 9:18:37 AM
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
            function loadData() {
                $("#divmsg").empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/loadConfigData.action',
                    data: {data: "init"},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        var container = $('<div />');
                        $.each(data.nameList, function (key, des) {
                            container.append('<div class="col-sm-8"><div class="form-group"><label >' + des + '</label><input type="text" name="' + key + '"  id="' + key + '" class="form-control" /></div></div>');
                        });
                        $('#dynamic').html(container);

                        $.each(data.nameListVal, function (key, value) {
                            $("#" + key).val(value);
                        });
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function  resetFieldData() {}



        </script>

        <title></title>
    </head>
    <body onload="loadData()">
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
            <div class="tb-breadcrumb">System Config > Data</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form cssClass="form" id="template" method="post" action="ConfigData" theme="simple" >
                        <div class="row" id="dynamic">
                        </div>

                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group  ">
                                    <span class="mandatoryfield">Mandatory fields are marked with *</span>
                                </div>
                            </div>
                        </div>
                        <div class="row"></div>
                        <div class="row form-inline">
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <sj:submit button="true" 
                                               value="Reset" 
                                               name="reset" 
                                               onClick="loadData()"
                                               cssClass="uinew-button-reset"
                                               />
                                </div>
                                <div class="form-group">
                                    <s:url var="updateurl" action="updateConfigData"/>
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
            </div>
            <div class="tb-table">
            </div> 
        </div> 
    </body>
</html>
