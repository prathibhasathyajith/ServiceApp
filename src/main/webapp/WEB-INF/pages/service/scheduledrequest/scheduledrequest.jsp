<%-- 
    Document   : scheduledrequest
    Created on : Jun 7, 2018, 1:44:59 PM
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
            function viewformatter(cellvalue) {
                return "<a href='#' title='View' onClick='javascript:viewScheReqInit(&#34;" + cellvalue + "&#34;)' title='View Audit Record'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function viewScheReqInit(keyval) {
                $("#viewdialog").data('id', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailScheduledReq.action?id=" + $led.data('id'));
            });
            function searchReq(param) {

                var cusId = $('#cusId').val();
                var bassType = $('#bassType').val();
                var latitude = $('#latitude').val();
                var longitude = $('#longitude').val();
                var description = $('#description').val();
                var address = $('#address').val();
                var fdate = $('#fdate').val();
                var tdate = $('#tdate').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cusId: cusId,
                        bassType: bassType,
                        latitude: latitude,
                        longitude: longitude,
                        description: description,
                        address: address,
                        fdate: fdate,
                        tdate: tdate,
                        search: param
                    }});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('completetopics', function (event, data) {
                var count = jQuery('#gridtable').jqGrid('getGridParam', 'reccount');
                if (count > 0) {
//                    $("#subview").removeAttr('disabled');
//                    $("#subview1").removeAttr('disabled');
//                    $("#subview").removeClass('ui-state-disabled');
//                    $("#subview1").removeClass('ui-state-disabled');
                    $('#subview').button("enable");
                    $('#subview1').button("enable");
                }
            });
            function setdate() {
                $("#fdate").datepicker("setDate", new Date());
                $("#tdate").datepicker("setDate", new Date());
            }

            function resetAllData() {


                $('#cusId').val("");
                $('#bassType').val("");
                $('#latitude').val("");
                $('#longitude').val("");
                $('#description').val("");
                $('#address').val("");
                $('#fdate').val("");
                $('#tdate').val("");
                $('#divmsg').text("");
                $('#subview').button("disable");
                $('#subview1').button("disable");

                setdate();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        cusId: '',
                        bassType: '',
                        latitude: '',
                        longitude: '',
                        description: '',
                        address: '',
                        fdate: '',
                        tdate: '',
                        search: false
                    }});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });
            function search(e) {
                var key = e.keyCode || e.which;
                if (key == 13) {
                    searchReason(true);
                }
            }
        </script>
        <title></title>
    </head>
    <body onload="setdate()">
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
            <div class="tb-breadcrumb">Service > Scheduled Requests </div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="scheduledReq" method="post" action="ScheduledReq" theme="simple" cssClass="form" >
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >From Date</label>
                                    <sj:datepicker cssClass="form-control" id="fdate" name="fdate" readonly="true" maxDate="d" changeYear="true"
                                                   buttonImageOnly="true" displayFormat="yy-mm-dd" yearRange="2000:2200" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >To Date</label>
                                    <sj:datepicker cssClass="form-control" id="tdate" name="tdate" readonly="true" maxDate="+1d" changeYear="true"
                                                   buttonImageOnly="true" displayFormat="yy-mm-dd" yearRange="2000:2200"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Customer ID</label>
                                    <s:textfield  name="cusId" id="cusId" maxLength="4" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>

                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Bass Type</label>
                                    <s:select  id="bassType" list="%{bassTypeList}"  headerValue="--Select Type--" headerKey="" name="bassType" listKey="roleType" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Latitude</label>
                                    <s:textfield  name="latitude" id="latitude" maxLength="15" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>

                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Longitude</label>
                                    <s:textfield  name="longitude" id="longitude" maxLength="15" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9.]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>

                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Description</label>
                                    <s:textfield  name="description" id="description" maxLength="128" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Address</label>
                                    <s:textfield  name="address" id="address" maxLength="128" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                                    onclick="searchReq(true)"
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
                    title="View Scheduled Requests"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="590"
                    dialogClass= "dialogclass"
                    />
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listScheduledReq"/>
                <s:set var="pcaption">Scheduled Service Requests</s:set>
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
                    onCompleteTopics="completetopics"
                    rowTotal="false"
                    viewrecords="true"
                    onErrorTopics="anyerrors">                                    

                    <sjg:gridColumn name="id" title="View" width="50" align="center"  formatter="viewformatter" frozen="false"/>
                    <sjg:gridColumn name="cusId" index="mobUser.id" title="Customer ID" width="50" sortable="true" frozen="false"/>
                    <sjg:gridColumn name="bassType" index="roles.roleType" width="250" title="Bass Type" sortable="true"/>
                    <sjg:gridColumn name="latitude" index="latitude" width="250" title="Latitude" sortable="true"/>
                    <sjg:gridColumn name="longitude" index="longitude" width="250" title="Longitude" sortable="true"/>
                    <sjg:gridColumn name="description" index="description" width="250" title="Description" sortable="true"/>
                    <sjg:gridColumn name="address" index="address" width="250" title="Address" sortable="true"/>
                    <sjg:gridColumn name="scheduledDate" index="scheduledDateTime" width="250" title="Scheduled Date" sortable="true"/>
                    <sjg:gridColumn name="createdTime" index="createdTime" title="Created Time"  sortable="true" />


                </sjg:grid>

            </div>
        </div>
    </body>
</html>
