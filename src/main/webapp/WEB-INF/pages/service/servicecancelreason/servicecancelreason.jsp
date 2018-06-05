<%-- 
    Document   : servicecancelreason
    Created on : Jun 5, 2018, 11:31:37 AM
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
                return "<a href='#' title='View' onClick='javascript:viewReasonInit(&#34;" + cellvalue + "&#34;)' title='View Audit Record'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function viewReasonInit(keyval) {
                $("#viewdialog").data('serviceId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailServiceCancelReason.action?serviceId=" + $led.data('serviceId'));
            });

            function searchReason(param) {

                var serviceId = $('#serviceId').val();
                var initUser = $('#initUser').val();
                var reason = $('#reason').val();
                var fdate = $('#fdate').val();
                var tdate = $('#tdate').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        serviceId: serviceId,
                        initUser: initUser,
                        reason: reason,
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

                $('#serviceId').val("");
                $('#initUser').val("");
                $('#reason').val("");
                $('#fdate').val("");
                $('#tdate').val("");
                $('#divmsg').text("");
                $('#subview').button("disable");
                $('#subview1').button("disable");
                
                setdate();
                
                $("#gridtable").jqGrid('setGridParam', {postData: {
                        serviceId: '',
                        initUser: '',
                        reason: '',
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
            <div class="tb-breadcrumb">Service > Service Cancel Reason</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="serviceCancelReason" method="post" action="ServiceCancelReason" theme="simple" cssClass="form" >
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
                                    <label >Service ID</label>
                                    <s:textfield  name="serviceId" id="serviceId" maxLength="4" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>

                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Initiated User</label>
                                    <s:textfield  name="initUser" id="initUser" maxLength="4" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Reason</label>
                                    <s:textfield  name="reason" id="reason" maxLength="128" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" 
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
                                    onclick="searchReason(true)"
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
                    title="View Reason"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="590"
                    dialogClass= "dialogclass"
                    />
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listServiceCancelReason"/>
                <s:set var="pcaption">Service Cancel Reason</s:set>
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

                    <sjg:gridColumn name="serviceId" title="View" width="50" align="center"  formatter="viewformatter" frozen="false"/>
                    <sjg:gridColumn name="serviceId" index="mobServiceRequest.serviceId" title="Service ID" width="50" sortable="true" frozen="false"/>
                    <sjg:gridColumn name="initUser" index="mobUser.id" title="Initiated User" width="50" sortable="true" frozen="false"/>
                    <sjg:gridColumn name="reason" index="reason" width="250" title="Reason" sortable="true"/>
                    <sjg:gridColumn name="createdTime" index="createdTime" title="Created Time"  sortable="true" />


                </sjg:grid>

            </div>
        </div>
        </div>
    </body>
</html>
