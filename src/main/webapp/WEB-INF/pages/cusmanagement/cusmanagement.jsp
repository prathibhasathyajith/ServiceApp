<%-- 
    Document   : systemuser
    Created on : Mar 20, 2018, 10:43:43 AM
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

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Edit' onClick='javascript:editCustomerMgtInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteCustomerMgtInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editCustomerMgtInit(keyval) {
                $("#updatedialog").data('userId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {

                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailCustomerMgt.action?userId=" + $led.data('userId'));
            });


            function deleteCustomerMgtInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to deactivate Customer ' + keyval + ' ?');
                return false;
            }

            function deleteCustomerMgt(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteCustomerMgt.action',
                    data: {userId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function resetSearchData() {
                $('#mobileSearch').val("");
                $('#fnameSearch').val("");
                $('#lnameSearch').val("");
                $('#emailSearch').val("");
                $('#nicSearch').val("");
                $('#statusSearch').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        mobileSearch: '',
                        fnameSearch: '',
                        lnameSearch: '',
                        emailSearch: '',
                        nicSearch: '',
                        statusSearch: '',
                        search: false
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            function searchCustomerMgt() {

                var mobileSearch = $('#mobileSearch').val();
                var fnameSearch = $('#fnameSearch').val();
                var status = $('#statusSearch').val();
                var lnameSearch = $('#lnameSearch').val();
                var emailSearch = $('#emailSearch').val();
                var nicSearch = $('#nicSearch').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        mobileSearch: mobileSearch,
                        fnameSearch: fnameSearch,
                        lnameSearch: lnameSearch,
                        emailSearch: emailSearch,
                        nicSearch: nicSearch,
                        statusSearch: status,
                        search: true
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function resetFieldData() {
                $('#transactiontypecode').val("");
                $('#description').val("");
                $('#description_receiver').val("");
                $('#status').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
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
            <div class="tb-breadcrumb">Customer Mgt > Customer Management</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="customermgtsearch" method="post" action="CustomerMgt" theme="simple" cssClass="form" >

                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Mobile No</label>
                                    <s:textfield name="mobileSearch" id="mobileSearch" maxLength="12" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>NIC </label>
                                    <s:textfield  name="nicSearch" id="nicSearch" maxLength="12" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9Vv]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^Vv0-9]/g,''))"/>
                                </div>                                      
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>First Name </label>
                                    <s:textfield  name="fnameSearch" id="fnameSearch" maxLength="128" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>                                      
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Last Name </label>
                                    <s:textfield  name="lnameSearch" id="lnameSearch" maxLength="128" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>                                      
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Email </label>
                                    <s:textfield  name="emailSearch" id="emailSearch" maxLength="256" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9@.]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9@.]/g,''))"/>
                                </div>                                      
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:select  id="statusSearch" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="statusSearch" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>
                <div class="row form-inline">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <sj:submit 
                                button="true"
                                value="Search" 
                                onClick="searchCustomerMgt()"  
                                disabled="#vsearch"
                                id="searchbut"
                                cssClass="uinew-button-submit"
                                />
                        </div>
                        <div class="form-group">                               
                            <sj:submit 
                                button="true" 
                                id="cancelsearch"
                                value="Reset" 
                                onClick="resetSearchData()"
                                cssClass="uinew-button-reset"
                                /> 
                        </div>
                    </div>
                    <!--<div class="col-sm-5"></div>-->
                    <!--                    <div class="col-sm-3 text-right">
                                            <div class="form-group">
                    <%--<s:url var="addurl" action="ViewPopupCustomerMgt"/>--%>   
                    <%--<sj:submit--%>                                                      
                        openDialog="remotedialog"
                        button="true"
                        href="%{addurl}"
                        disabled="#vadd"
                        value="Add New Transaction Type"
                        id="addButton"
                        targets="remotedialog"   
                        cssClass="uinew-button-submit"
                        /> 
                </div>
            </div> -->
                </div> 
                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update Customer"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="700"
                    dialogClass= "dialogclass"
                    />
                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add Customer"                            
                    loadingText="Loading .."                            
                    position="center"                            
                    width="900"
                    height="490"
                    dialogClass= "dialogclass"
                    />
                <!-- Start delete confirm dialog box -->
                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteCustomerMgt($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Deactivate Customer"                            
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
                <s:url var="listurl" action="ListCustomerMgt"/>
                <s:set var="pcaption">Customer Management</s:set>

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
                    <sjg:gridColumn name="id" index="u.id" title="Edit" width="40" align="center" sortable="false" formatter="editformatter"  frozen="false"/>
                    <sjg:gridColumn name="id" index="u.id" title="Deactivate" width="50" align="center" sortable="false" formatter="deleteformatter"  frozen="false"/>  
                    <sjg:gridColumn name="id" index="u.id" title="User ID"  sortable="true" frozen="false"/>
                    <sjg:gridColumn name="mobile" index="u.mobile" title="Mobile No"  sortable="true"/>
                    <sjg:gridColumn name="email" index="u.email" title="Email"  sortable="true"/>
                    <sjg:gridColumn name="firstName" index="u.firstName" title="First Name"  sortable="true"/>
                    <sjg:gridColumn name="lastName" index="u.lastName" title="Last Name"  sortable="true"/>
                    <sjg:gridColumn name="nic" index="u.nic" title="NIC"  sortable="true"/>
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true"/>
                    <%--<sjg:gridColumn name="lastLoginTime" index="u.lastLoginTime" title="last Login Time"  sortable="true"/>--%>
                    <%--<sjg:gridColumn name="createdTime" index="u.createdTime" title="Created time"  sortable="true"/>--%>

                </sjg:grid> 
            </div>
        </div>
    </body>
</html>
