<%-- 
    Document   : servicerequest
    Created on : Jun 11, 2018, 10:17:43 AM
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
        <script src="${pageContext.request.contextPath}/resources/assets/Js/monthpicker.js" ></script>
        <script type="text/javascript">
            function viewformatter(cellvalue) {
                return "<a href='#' title='View' onClick='javascript:viewServiceReqInit(&#34;" + cellvalue + "&#34;)' title='View Audit Record'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function viewServiceReqInit(keyval) {
                $("#viewdialog").data('serviceId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailServiceRequest.action?serviceId=" + $led.data('serviceId'));
            });
            function searchSerReq(param) {

                var cusfname = $('#cusfname').val();
                var bassfname = $('#bassfname').val();
                var status = $('#status').val();
                var custAddress = $('#custAddress').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cusfname: cusfname,
                        bassfname: bassfname,
                        status: status,
                        custAddress: custAddress,
                        search: param
                    }});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetAllData() {


                $('#cusfname').val("");
                $('#bassfname').val("");
                $('#status').val("");
                $('#custAddress').val("");

                $('#divmsg').text("");

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cusfname: '',
                        bassfname: '',
                        status: '',
                        custAddress: '',
                        search: false
                    }});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            $(function () {
                $('input.monthpicker').monthpicker({
                    changeYear: true,
                    minDate: "-5 Y",
                    maxDate: "+0 Y",
                    onSelect: function() {
                        $("#summaryButton").button("enable");
                    },
                    dateFormat: 'yy-mm'
                });
            }
            );

            function summaryReport() {

                var month = $("#month").val();

                var monthSplit = month.split("-");
                var monthNum = Number(monthSplit[1]);
                var monthPlus;

                if (1 <= monthNum && monthNum < 12) {
                    monthPlus = monthNum + 1;
                    if (monthPlus < 10) {
                        monthPlus = Number(monthSplit[0]) + "-0" + monthPlus;
                    } else {
                        monthPlus = Number(monthSplit[0]) + "-" + monthPlus;
                    }
                } else if (monthNum === 12) {
                    monthPlus = Number(monthSplit[0]) + 1 + "-01";
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/summaryServiceRequest',
                    data: {month: month, monthPlus: monthPlus},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $(".service-summary-table").show();
                        alert(data.statusWise_req_count);
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
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
            <div class="tb-breadcrumb">Service > Service Request</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <div class="row ">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label >Select Month</label>
                                <input class="form-control monthpicker" id="month" name="month" />
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <sj:submit button="true" disabled="true" value="Get Summary" id="summaryButton" onclick="summaryReport()" cssClass="uinew-button-submit2" />
                            </div>
                        </div>
                    </div>

                    <table class="service-summary-table" style="display: none">
                        <tbody>
                            <tr>
                                <td>Transaction Id</td>
                                <td>:</td>
                                <td>12231092381238401</td>
                            </tr>
                            <tr>
                                <td>Transaction RefNo</td>
                                <td>:</td>
                                <td>122</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="serviceReco" method="post" action="ServiceRequest" theme="simple" cssClass="form" >
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Customer First Name</label>
                                    <s:textfield  name="cusfname" id="cusfname" maxLength="20" cssClass="form-control" />

                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Bass First Name</label>
                                    <s:textfield  name="bassfname" id="bassfname" maxLength="20" cssClass="form-control" />

                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Customer Address</label>
                                    <s:textfield  name="custAddress" id="custAddress" maxLength="128" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Status</label>
                                    <s:select  id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="status" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                    </s:form>
                    <div class="row "></div>
                    <div class="row form-inline">
                        <div class="col-sm-8">
                            <div class="form-group">
                                <sj:submit  
                                    value="Search"
                                    button="true" 
                                    id="searchButton"
                                    onclick="searchSerReq(true)"
                                    cssClass="uinew-button-submit"
                                    />
                            </div>
                            <div class="form-group">
                                <sj:submit 
                                    button="true" 
                                    value="Reset" 
                                    name="reset" 
                                    onClick="resetAllData()" 
                                    cssClass="uinew-button-reset"
                                    /> 
                            </div>
                        </div>
                    </div>
                </div>
                <sj:dialog                                     
                    id="viewdialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="View Service Requests"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="590"
                    dialogClass= "dialogclass"
                    />
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listServiceRequest"/>
                <s:set var="pcaption">Service Requests</s:set>
                <sjg:grid 
                    id="gridtable"
                    caption="%{pcaption}"
                    dataType="json"
                    href="%{listurl}"
                    pager="true"
                    gridModel="gridModel"
                    rowList="10,15,20"
                    rowNum="10"
                    autowidth="true"
                    rownumbers="true"
                    onGridCompleteTopics="gridComplete"
                    rowTotal="false"
                    viewrecords="true"
                    onErrorTopics="anyerrors">                                    

                    <sjg:gridColumn name="serviceId" title="View" width="70" align="center"  formatter="viewformatter" frozen="false"/>
                    <sjg:gridColumn name="serviceId" index="serviceId" title="Service Id" width="50" sortable="true" frozen="false" hidden="true"/>
                    <sjg:gridColumn name="cusId" index="mobUser.id" width="250" title="Customer ID" sortable="true"/>
                    <sjg:gridColumn name="cusfname" index="mobUser.firstName" width="250" title="Customer First Name" sortable="true"/>
                    <sjg:gridColumn name="bassId" index="mobBassData.userId" width="250" title="Bass ID" sortable="true"/>
                    <sjg:gridColumn name="bassfname" index="mobBassData.mobUser.firstName" width="250" title="Bass First Name" sortable="true"/>
                    <sjg:gridColumn name="status" index="status.description" width="250" title="Status" sortable="true"/>
                    <sjg:gridColumn name="custAddress" index="custAddress" width="250" title="Customer Address" sortable="true"/>
                    <sjg:gridColumn name="latitude" index="latitude" width="250" title="Latitude" sortable="true"/>
                    <sjg:gridColumn name="longitude" index="longitude" width="250" title="Longitude" sortable="true"/>
                    <sjg:gridColumn name="updatedTime" index="updatedTime" title="Updated Time"  sortable="true" />
                    <sjg:gridColumn name="createdTime" index="createdTime" title="Created Time"  sortable="true" />

                </sjg:grid>

            </div>
        </div>
    </body>
</html>
