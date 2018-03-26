<%-- 
    Document   : emailtemplate
    Created on : Mar 26, 2018, 4:42:58 PM
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

            function resetFieldData() {

//                $("#a_catId").val("");
//                $("#a_description").val("");
//                $("#a_sortOrder").val("");
//                $("#a_status").val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function searchCategory() {
                var s_messageid = $('#s_messageid').val();
                var s_subject = $('#s_subject').val();
                var transactiontypesearch = $('#transactiontypesearch').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        s_messageid: s_messageid,
                        s_subject: s_subject,
                        transactiontypesearch: transactiontypesearch,
                        search: true

                    }});

                jQuery("#gridtable").jqGrid('setGridParam', {datatype: 'json'}).trigger("reloadGrid");

            }

            function editformatter(cellvalue, options, rowObject) {

                return "<a href='#/' title='Edit' onClick='javascript:editEmail(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block; margin-left: auto; margin-right: auto;'/></a>";
            }

            function editEmail(keyval) {

                $("#updatedialog").data('messageid', keyval).dialog('open');

            }

            $.subscribe('openviewtasktopage', function (event, data) {

                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailEmailTemplate.action?messageid=" + $led.data('messageid'));

            });


            function resetAllData() {

                $('#s_messageid').val("");
                $('#s_subject').val("");
                $('#transactiontypesearch').val("");


                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        s_messageid: '',
                        s_subject: '',
                        transactiontypesearch: '',
                        search: false
                    }
                });

                $("#gridtable").jqGrid('setGridParam', {datatype: 'json'}, {page: 1});
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
                    <s:form cssClass="form" id="template" method="post" action="EmailTemplate" theme="simple" >
                        <!--                                    not use-->
                        <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                        <s:url var="addurl" action="viewpopupMobUtilityCategory"/>
                        <s:url var="updateurl" action="UpdateVehicleModelMgt"/>
                        <!--                                    not use-->

                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Message ID</label>
                                    <s:textfield name="s_messageid" id="s_messageid" maxLength="20" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9]/g,''))" /> 
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Transaction Type</label>
                                    <s:select cssClass="form-control" name="transactiontype" maxLength="3" id="transactiontypesearch" headerValue="-- Select Transaction Type--" list="%{transactiontypeList}"   headerKey="" listKey="typecode" listValue="description" value="%{transactiontype}"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label >Subject</label>
                                    <s:textfield name="s_subject" id="s_subject" maxLength="128" cssClass="form-control" /> 
                                </div>
                            </div
                        </div>

                    </s:form>

                    <div class="row form-inline">
                        <div class="col-sm-4">
                            <div class="form-group">                                                   
                                <sj:submit 
                                    button="true"
                                    value="Search" 
                                    onclick="searchCategory()"  
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
                </div>
                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update Email Template"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="950"
                    height="550"
                    dialogClass= "dialogclass"
                    /> 
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listEmailTemplate"/>
                <s:set var="pcaption">Email Template</s:set>


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

                    <sjg:gridColumn name="messageid" index="messageid" title="Edit" width="30" align="center" formatter="editformatter"  sortable="false"/>
                    <sjg:gridColumn name="messageid" index="messageid" title="Message ID" />
                    <sjg:gridColumn name="transactiontype" index="transactiontype" title="Transaction Type" />
                    <sjg:gridColumn name="subject" index="subject" title="Subject"  />
                    <sjg:gridColumn name="createdtime" index="createdtime" title="Created Time"   />

                </sjg:grid> 
            </div> 
        </div> 
    </body>
</html>
