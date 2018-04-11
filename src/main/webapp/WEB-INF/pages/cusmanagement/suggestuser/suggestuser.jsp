<%-- 
    Document   : suggestuser
    Created on : Apr 10, 2018, 3:13:52 PM
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
                return "<a href='#' title='View' onClick='javascript:viewSuggestUserInit(&#34;" + cellvalue + "&#34;)' title='View Audit Record'><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
            }
            function viewSuggestUserInit(keyval) {
                $("#viewdialog").data('id', keyval).dialog('open');
            }

            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("viewDetailSuggestUser.action?id=" + $led.data('id'));
            });

            function searchSuggestUser() {

                var search_firstname = $('#search_firstname').val();
                var search_lastname = $('#search_lastname').val();
                var search_email = $('#search_email').val();
                var search_mobile = $('#search_mobile').val();
                var search_status = $('#search_status').val();
                var search_role = $('#search_role').val();

                var search_name_user = $('#search_name_user').val();
                var search_mobile_user = $('#search_mobile_user').val();


                $("#gridtable").jqGrid('setGridParam', {postData: {
                        search_firstname: search_firstname,
                        search_lastname: search_lastname,
                        search_email: search_email,
                        search_mobile: search_mobile,
                        search_status: search_status,
                        search_role: search_role,
                        search_name_user: search_name_user,
                        search_mobile_user: search_mobile_user,
                        search: true
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
            });

            function resetSearchData() {
                $('#search_firstname').val("");
                $('#search_lastname').val("");
                $('#search_email').val("");
                $('#search_mobile').val("");
                $('#search_status').val("");
                $('#search_role').val("");

                $('#search_name_user').val("");
                $('#search_mobile_user').val("");

                $("#gridtable").jqGrid('setGridParam', {postData: {
                        search_firstname: '',
                        search_lastname: '',
                        search_email: '',
                        search_mobile: '',
                        search_status: '',
                        search_role: '',
                        search_name_user: '',
                        search_mobile_user: '',
                        search: false
                    }});

                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function resetFieldData() {
                $('#search_firstname').val("");
                $('#search_lastname').val("");
                $('#search_email').val("");
                $('#search_mobile').val("");
                $('#search_status').val("");
                $('#search_role').val("");

                $('#search_name_user').val("");
                $('#search_mobile_user').val("");

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
            <div class="tb-breadcrumb">Customer Mgt > Suggested User</div>
            <div class="tb-form">
                <div class="containe-fluid">
                    <s:form id="SuggestUser" method="post" action="SuggestUser" theme="simple" cssClass="form" >
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>First Name</label>
                                    <s:textfield name="search_firstname" id="search_firstname" maxLength="64" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Last Name</label>
                                    <s:textfield  name="search_lastname" id="search_lastname" maxLength="128" cssClass="form-control" />
                                </div>                                      
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Email</label>
                                    <s:textfield  name="search_email" id="search_email" maxLength="128" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Mobile</label>
                                    <s:textfield  name="search_mobile" id="search_mobile" maxLength="12" cssClass="form-control" onkeyup="$(this).val($(this).val().replace(/[^0-9+#-]/g,''))" onmouseout="$(this).val($(this).val().replace(/[^0-9+#-]/g,''))"/>
                                </div>                                      
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Role</label>
                                    <s:select  id="search_role" list="%{serviceroleList}"  headerValue="--Select Role--" headerKey="" name="search_role" listKey="roleType" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:select  id="search_status" list="%{statusList}"  headerValue="--Select Status--" headerKey="" name="search_status" listKey="statuscode" listValue="description" disabled="false"  cssClass="form-control"/>
                                </div>                                      
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Referrer Name</label>
                                    <s:textfield  name="search_name_user" id="search_name_user" maxLength="64" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Referrer Mobile</label>
                                    <s:textfield  name="search_mobile_user" id="search_mobile_user" maxLength="128" cssClass="form-control" />
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
                                onClick="searchSuggestUser()"  
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
                </div>
                <sj:dialog                                     
                    id="viewdialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="View Suggested User"
                    onOpenTopics="openviewtasktopage" 
                    loadingText="Loading .."
                    width="900"
                    height="590"
                    dialogClass= "dialogclass"
                    />
                <sj:dialog                                     
                    id="updatedialog"                                 
                    autoOpen="false" 
                    modal="true" 
                    position="center"
                    title="Update Suggest User"
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
                    title="Add Suggest User"                            
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
                    'OK':function() { deleteSuggestUser($(this).data('keyval'));$( this ).dialog( 'close' ); },
                    'Cancel':function() { $( this ).dialog( 'close' );} 
                    }" 
                    autoOpen="false" 
                    modal="true" 
                    title="Delete Suggest User"                            
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
                <s:url var="listurl" action="ListSuggestUser"/>
                <s:set var="pcaption">Suggested User</s:set>
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
                    <sjg:gridColumn name="id" index="u.id" title="View" width="50" align="center"  formatter="viewformatter" frozen="false"/>
                    <sjg:gridColumn name="id" index="u.id" title="Id"  sortable="true" frozen="false"/>
                    <sjg:gridColumn name="firstName" index="u.firstName" title="First Name"  sortable="true"/>
                    <sjg:gridColumn name="lastName" index="u.lastName" title="Last Name"  sortable="true"/>
                    <sjg:gridColumn name="email" index="u.email" title="Email"  sortable="true"/>
                    <sjg:gridColumn name="mobile" index="u.mobile" title="Mobile"  sortable="true"/>
                    <sjg:gridColumn name="area" index="u.area" title="Area"  sortable="true"/>
                    <sjg:gridColumn name="status" index="u.status" title="Status"  sortable="true"/>
                    <sjg:gridColumn name="serviceRole" index="u.serviceRole" title="Service Role"  sortable="true"/>
                    <sjg:gridColumn name="firstName_user" index="u.mobUser.firstName" title="Referrer Name"  sortable="true"/>
                    <sjg:gridColumn name="createdTime" index="u.createdTime" title="Created Time"  sortable="true"/>

                </sjg:grid> 
            </div>
        </div>
    </body>
</html>


