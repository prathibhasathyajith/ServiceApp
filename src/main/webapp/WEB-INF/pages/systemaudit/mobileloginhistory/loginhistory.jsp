<%-- 
    Document   : loginhistory
    Created on : Mar 27, 2018, 3:37:25 PM
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
                return "<a href='viewDetailEventHistory.action?auditId=" + cellvalue + "' title='View Audit Record' ><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function searchAudit() {
                var id = $('#id').val();
                var userid = $('#userid').val();
                var username = $('#username').val();
                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        id: id,
                        userid: userid,
                        username: username,
                        fromdate: fromdate,
                        todate: todate,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetAllData() {

                $('#id').val("");
                $('#userid').val("");
                $('#username').val("");

                setdate();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        id: '',
                        userid: '',
                        username: '',
                        fromdate: '',
                        todate: '',
                        search: false
                    }
                });

                jQuery("#gridtable").trigger("reloadGrid");
            }



            $.subscribe('anyerrors', function (event, data) {
                //                   alert('status: ' + event.originalEvent.status + '\n\nrequest status: ' +event.originalEvent.request.status+ '\n\nerror: ' + event.originalEvent.error);
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });




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
            
            // change column width of row count
            $.subscribe('gridComplete', function (event, data) {

                var rowLen = $("td.ui-state-default.jqgrid-rownum").eq(0).text().length;
                if (rowLen >= 4) {
                    var pix = rowLen * 5 + 15 + "px";
                    $("#gridtable_frozen > tbody > tr.jqgfirstrow").eq(0).children(0).eq(0).css("width", pix);
                    $("#gridtable > tbody > tr.jqgfirstrow").eq(0).children(0).eq(0).css("width", pix);
                    $(".frozen-div #gridtable_rn").css("width", pix);
                    $("#gridtable_rn").css("width", pix);
                } else {
                    $("#gridtable_frozen > tbody > tr.jqgfirstrow").eq(0).children(0).eq(0).css("width", "25px");
                    $("#gridtable > tbody > tr.jqgfirstrow").eq(0).children(0).eq(0).css("width", "25px");
                    $(".frozen-div #gridtable_rn").css("width", "25px");
                    $("#gridtable_rn").css("width", "25px");
                }
            });

            function setdate() {
                $("#fromdate").datepicker("setDate", new Date());
                $("#todate").datepicker("setDate", new Date());
            }
//
//            $(document).ready(function () {
//                $("#mobile").change(function () {
//                    $("#username").attr('disabled', 'disabled');
//                });
//                $("#username").change(function () {
//                    $("#mobile").attr('disabled', 'disabled');
//                });
//            });

            function todo() {
                //            window.open();
                $('#reporttype').val("pdf");
                form = document.getElementById('auditform');
                //                    form.target = '_blank';
                form.action = 'reportGenerateLoginHistory';
                form.submit();
                //            window.open(form);
                $('#subview').button("disable");
                $('#subview1').button("disable");
            }

            function todoexel() {
                //            window.open();
                $('#reporttype').val("exel");
                form = document.getElementById('auditform');
                //                    form.target = '_blank';
                form.action = 'reportGenerateLoginHistory';
                form.submit();
                //            window.open(form);
                $('#subview').button("disable");
                $('#subview1').button("disable");
            }

            function search(e) {
                var key = e.keyCode || e.which;
                if (key == 13) {
                    searchAudit();
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
            <div class="tb-breadcrumb">System Audit > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="auditform" method="post" theme="simple" cssClass="form">
                        <s:hidden name="reporttype" id="reporttype"/>                                    
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>From Date</label>
                                    <sj:datepicker cssClass="form-control" id="fromdate" name="fromdate" readonly="true" changeYear="true" yearRange="2000:2200"
                                                   maxDate="d" buttonImageOnly="true" displayFormat="yy-mm-dd"  />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >To Date</label>
                                    <sj:datepicker  cssClass="form-control" id="todate" name="todate" readonly="true" changeYear="true" yearRange="2000:2200"
                                                    maxDate="+1d" buttonImageOnly="true" displayFormat="yy-mm-dd"  />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >User ID</label>
                                    <s:textfield cssClass="form-control" name="userid" id="userid" onkeypress="search(event)" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9]/g,''))" maxLength="10" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Username</label>
                                    <s:textfield cssClass="form-control" name="username" id="username" maxLength="35" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onkeypress="search(event)"/>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>User Type</label>
                                    <s:select cssClass="form-control" id="userType" list="%{userTypeList}" name="type"
                                              headerKey="" headerValue="--Select User Type--"
                                              listKey="key" listValue="value" />
                                </div>
                            </div>
                        </div>

                    </s:form>
                    <div class="row "></div>
                    <div class="row  form-inline">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <sj:submit  
                                    value="Search"
                                    button="true" 
                                    id="searchButton"
                                    onclick="searchAudit(true)"
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
                            <div class="form-group">
                                <!sj:submit 
                                    cssClass="uinew-button-submit"
                                    button="true" 
                                    value="View PDF" 
                                    name="subview" 
                                    id="subview" 
                                    onClick="todo()" 
                                    disabled="#vgenerate"/> 
                            </div>

                            <div class="form-group">
                                <!sj:submit 
                                    cssClass="uinew-button-submit"
                                    button="true" 
                                    value="View Excel" 
                                    name="subview1" 
                                    id="subview1" 
                                    onClick="todoexel()" 
                                    disabled="#vgenerate"/> 
                            </div>
                        </div>

                    </div>
                </div>
                <sj:dialog 
                    id="adddialog" 
                    buttons="{ 
                    'OK':function() { AddSection($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Add Section"                            
                    />
                <!-- Start add process dialog box -->
                <sj:dialog 
                    id="addsuccdialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}
                    }"  
                    autoOpen="false" 
                    modal="true" 
                    title="Adding Process." 
                    />
                <!--end newly changed-->

                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteTask($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Task"                            
                    />
                <!-- Start delete process dialog box -->
                <sj:dialog 
                    id="deletesuccdialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}
                    }"  
                    autoOpen="false" 
                    modal="true" 
                    title="Deleting Process." 
                    />
                <!-- Start delete error dialog box -->
                <sj:dialog 
                    id="deleteerrordialog" 
                    buttons="{
                    'OK':function() { $( this ).dialog( 'close' );}                                    
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete error."
                    />
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listLoginHistory"/>
                <s:set var="pcaption">Mobile Login History</s:set>

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
                    onCompleteTopics="completetopics"
                    rowTotal="false"                                    
                    viewrecords="true"
                    onErrorTopics="anyerrors"
                    >
                    <sjg:gridColumn name="id" index="u.id" title="ID"  sortable="true" frozen="false" hidden="true"/>
                    <sjg:gridColumn name="userid" index="u.userid" title="User ID"  sortable="true" frozen="false" />
                    <sjg:gridColumn name="username" index="u.username" title="Username"  sortable="true"/>
                    <sjg:gridColumn name="operation" index="u.operation" title="Operation" sortable="true" /> 
                    <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                    <sjg:gridColumn name="device" index="u.device" title="Device" sortable="true" /> 
                    <sjg:gridColumn name="type" index="u.type" title="User Type" sortable="true" /> 
                    <sjg:gridColumn name="ip" index="u.ip" title="IP"  sortable="true"/>
                    <sjg:gridColumn name="createdtime" index="u.createdtime" title="Created Time"  sortable="true"/>
                </sjg:grid>
            </div>
        </div>
    </body>
</html>