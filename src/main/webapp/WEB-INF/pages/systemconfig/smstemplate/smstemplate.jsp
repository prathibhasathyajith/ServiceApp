<%-- 
    Document   : smstemplate
    Created on : Mar 26, 2018, 4:43:23 PM
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



            function resetAllData() {

                $('#messageIdSearch').val("");
                $('#txnTypeSearch').val("");
                $('#descriptionSearch').val("");


                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        messageIdSearch: '',
                        txnTypeSearch: '',
                        descriptionSearch: '',
                        search: false
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {datatype: 'json'}, {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");


            }

            function editformatter(cellvalue) {
                return "<a href='#/' title='Edit' onClick='javascript:editSMSTemplateInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block; margin-left: auto; margin-right: auto;'/></a>";
            }

            function editSMSTemplateInit(keyval) {
                $("#updatedialog").data('messageId', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailSMSTemplate.action?messageId=" + $led.data('messageId'));
            });

            function resetFieldData() {



                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function cancelAllData() {
                editSMSTemplate(null);
            }





            function searchSMSTemplate() {
                var messageIdSearch = $('#messageIdSearch').val();
                var txnTypeSearch = $('#txnTypeSearch').val();
                var descriptionSearch = $('#descriptionSearch').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        messageIdSearch: messageIdSearch,
                        txnTypeSearch: txnTypeSearch,
                        descriptionSearch: descriptionSearch,
                        search: true

                    }});

                jQuery("#gridtable").jqGrid('setGridParam', {datatype: 'json'}).trigger("reloadGrid");

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
                    <s:form cssClass="form" id="smstempateform" method="post" action="SMSTemplate" theme="simple" >

                        <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                        <s:url var="updateurl" action="UpdateSMSTemplate"/>

                        <div class="row "> 
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Message ID</label>
                                    <s:textfield name="messageIdSearch" id="messageIdSearch" maxlength="20" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))"/> 
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Transaction Type</label>
                                    <s:select  id="txnTypeSearch" maxlength="3" list="%{txnTypeList}"  name="txnTypeSearch" headerKey=""  headerValue="--Select Type--" listKey="typecode" listValue="description"  disabled="false" cssClass="form-control" value="%{type}"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Message</label>
                                    <s:textfield name="descriptionSearch" id="descriptionSearch" maxlength="1024" cssClass="form-control"/> 
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
                                onclick="searchSMSTemplate()"  
                                id="searchbut"
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


                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update SMS Template"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="950"
                    height="550"
                    dialogClass= "dialogclass"
                    /> 
            </div>
            <!-- start table -->
            <div class="tb-table">
                <s:url var="listurl" action="listSMSTemplate"/>
                <s:set var="pcaption">SMS Template</s:set>

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
                    <sjg:gridColumn name="messageId" index="messageid" title="Edit" width="25" align="center" formatter="editformatter"  />
                    <sjg:gridColumn name="messageId" index="messageid" title="Message ID"  sortable="true"/>
                    <sjg:gridColumn name="txnType" index="transactiontype" title="Transaction Type"  sortable="true"/>
                    <sjg:gridColumn name="description" index="description" title="Message"  sortable="true"/>
                    <sjg:gridColumn name="createtime" index="createtime" title="Created Time"  sortable="true"/>
                </sjg:grid> 
            </div> 
        </div>
    </body>
</html>
