<%-- 
    Document   : servicecategory
    Created on : May 1, 2018, 10:40:33 AM
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


            function editformatter(cellvalue) {
                return "<a href='#/' title='Edit' onClick='javascript:editServiceCategoryInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-pencil' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }

            function editServiceCategoryInit(keyval) {
                $("#updatedialog").data('roleType', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#updatedialog");
                $led.html("Loading..");
                $led.load("detailServiceCategory.action?roleType=" + $led.data('roleType'));
            });


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#/' title='Delete' onClick='javascript:deleteServiceCategoryInit(&#34;" + cellvalue + "&#34;)'><img class='ui-icon ui-icon-trash' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function deleteServiceCategoryInit(keyval) {
                $('#divmsg').empty();
                $("#deletedialog").data('keyval', keyval).dialog('open');
                $("#deletedialog").html('Are you sure you want to delete service category ' + keyval + ' ?');
                return false;
            }

            function cancelAllData() {
                editServiceCategory(null);
            }

            function searchServCat(param) {

                var roleType = $('#roleType').val();
                var description = $('#description').val();
                var status = $('#status').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        roleType: roleType,
                        description: description,
                        status: status,
                        search: param
                    }});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }


            $.subscribe('completetopics', function (event, data) {
                var count = jQuery('#gridtable').jqGrid('getGridParam', 'reccount');
                if (count > 0) {
                    $('#subview').button("enable");
                    $('#subview1').button("enable");
                }
            });


            function resetAllData() {


                $('#roleType').val("");
                $('#description').val("");
                $('#status').val("");
                $('#divmsg').text("");


                $("#gridtable").jqGrid('setGridParam', {postData: {
                        roleType: '',
                        description: '',
                        status: '',
                        search: false
                    }});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {

                $('#iroleType').val("");
                $('#idescription').val("");
                $('#istatus').val("");
//                $('#divmsg').text("");

                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }




            function deleteServiceCategory(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/deleteServiceCategory',
                    data: {roleType: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $("#deletesuccdialog").dialog('open');
                        $("#deletesuccdialog").html(data.message);

                        resetFieldData();
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
            <div class="tb-breadcrumb">Service > Service Category</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="serviceCategory" method="post" action="ServiceCategory" theme="simple" cssClass="form" >
                        <s:hidden id="oldvalue" name="oldvalue" ></s:hidden>
                        <s:url var="addurl" action="viewpopupServiceCategory"/>
                        <s:url var="updateurl" action="UpdateServiceCategory"/>

                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Service Type Code</label>
                                    <s:textfield  name="roleType" id="roleType" maxLength="16" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))" 
                                                  onmouseout="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g, ''))"/>

                                </div>
                            </div> 
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Description</label>
                                    <s:textfield  name="description" id="description" maxLength="128" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label >Bass Type</label>
                                    <s:select  id="status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="bassType" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                        </div>
                    </s:form>


                    <div class="row "></div>
                    <div class="row form-inline">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <sj:submit  
                                    value="Search"
                                    button="true" 
                                    id="searchButton"
                                    onclick="searchServCat(true)"
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
                        <div class="col-sm-5"></div>
                        <div class="col-sm-3 text-right">
                            <div class="form-group">
                                <sj:submit 
                                    openDialog="remotedialog"
                                    button="true"
                                    href="%{addurl}"
                                    value="Add Service Category"
                                    id="addButton_new"
                                    targets="remotedialog"   
                                    cssClass="uinew-button-submit"
                                    />
                            </div>
                        </div>
                    </div>

                </div>

                <sj:dialog 
                    id="deletedialog" 
                    buttons="{ 
                    'OK':function() { deleteServiceCategory($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Service Category"                            
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

                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update Service Category"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="400"
                    dialogClass= "dialogclass"
                    />  
                <sj:dialog                                     
                    id="remotedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    title="Add Service Category"                            
                    loadingText="Loading .."                            
                    position="center"                            
                    width="900"
                    height="400"
                    dialogClass= "dialogclass"
                    />
            </div>
            <div class="tb-table">
                <s:url var="listurl" action="listServiceCategory"/>
                <s:set var="pcaption">Service Category</s:set>
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

                    <sjg:gridColumn name="roleType" title="Update" width="50" align="center"  formatter="editformatter" frozen="false"/>
                    <!--must delete from all foreign keys from all tables-->
                    <%--<sjg:gridColumn name="roleType" title="Delete" width="50" align="center"  formatter="deleteformatter" frozen="false"/>--%>
                    <sjg:gridColumn name="roleType" index="roleType" title="Service Type" sortable="true" frozen="false"/>
                    <sjg:gridColumn name="description" index="description" width="250" title="Description" sortable="true"/>
                    <sjg:gridColumn name="status" index="status.statuscode" width="250" title="Status" sortable="true"/>

                </sjg:grid>
            </div>
        </div>
    </body>
</html>
