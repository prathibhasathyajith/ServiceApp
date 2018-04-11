<%-- 
    Document   : transactiontype
    Created on : Mar 26, 2018, 4:43:46 PM
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
                return "<a href='#' title='Edit' onClick='javascript:editTransactionTypeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteTransactionTypeInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editTransactionTypeInit(keyval) {
                $("#updatedialog").data('transactiontypecode', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {

                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailTransactionType.action?transactiontypecode=" + $led.data('transactiontypecode'));
            });


            function deleteTransactionTypeInit(keyval) {
                $('#divmsg').empty();

                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete transaction type ' + keyval + ' ?');
                return false;
            }

            function deleteTransactionType(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteTransactionType.action',
                    data: {transactiontypecode: keyval},
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
                $('#transactiontypecodesearch').val("");
                $('#descriptionsearch').val("");
                $('#statussearch').val("");
                $('#description_receiversearch').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_transactiontypecode: '',
                        s_description: '',
                        s_status: '',
                        s_description_receiver: '',
                        search: false
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }
            function searchTransactionType() {

                var transactiontypecode = $('#transactiontypecodesearch').val();
                var description = $('#descriptionsearch').val();
                var status = $('#statussearch').val();
                var description_receiver = $('#description_receiversearch').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_transactiontypecode: transactiontypecode,
                        s_description: description,
                        s_status: status,
                        s_description_receiver: description_receiver,
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
            <div class="tb-breadcrumb">User Management > System User</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="transactiontypsearch" method="post" action="TransactionType" theme="simple" cssClass="form" >


                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Transaction Type Code </label>
                                    <s:textfield name="transactiontypecodesearch" id="transactiontypecodesearch" maxLength="2" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Description </label>
                                    <s:textfield  name="descriptionsearch" id="descriptionsearch" maxLength="64" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
                                </div>                                      
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:select  id="statussearch" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="statussearch" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Description Receiver</label>
                                    <s:textfield  name="description_receiversearch" id="description_receiversearch" maxLength="64" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/>
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
                                onClick="searchTransactionType()"  
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
                    <%--<s:url var="addurl" action="ViewPopupTransactionType"/>--%>   
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
                    title="Update Transaction Type"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="450"
                    dialogClass= "dialogclass"
                    />
                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add Transaction Type"                            
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
                    'OK':function() { deleteTransactionType($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Transaction Type"                            
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
                <s:url var="listurl" action="ListTransactionType"/>
                <s:set var="pcaption">Transaction Type</s:set>

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
                    <sjg:gridColumn name="transactiontypecode" index="u.typecode" title="Edit" width="40" align="center" sortable="false" formatter="editformatter"  frozen="false"/>
                    <sjg:gridColumn name="transactiontypecode" index="u.typecode" title="Delete" width="50" align="center" sortable="false" formatter="deleteformatter"  frozen="false"/>  
                    <sjg:gridColumn name="transactiontypecode" index="u.typecode" title="Transaction Type Code"  sortable="true" frozen="true"/>
                    <sjg:gridColumn name="description" index="u.description" title="Description"  sortable="true"/>
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true"/>
                    <sjg:gridColumn name="description_receiver" index="u.description_receiver" title="Description Receiver"  sortable="true"/>

                </sjg:grid> 
            </div>
        </div>
    </body>
</html>
